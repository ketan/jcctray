/*******************************************************************************
 *  Copyright 2007 Ketan Padegaonkar http://ketan.padegaonkar.name
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package net.sourceforge.jcctray.model;

import java.io.IOException;

import net.sourceforge.jcctray.exceptions.HTTPErrorException;
import net.sourceforge.jcctray.exceptions.InvocationException;
import net.sourceforge.jcctray.utils.StringUtils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * An abstract implementation of ICruise that can be connected to via HTTP.
 * 
 * @author Ketan Padegaonkar
 */
public abstract class HTTPCruise implements ICruise {


	private static HttpClient getClient(Host host) {
		SimpleHttpConnectionManager connectionManager =  new SimpleHttpConnectionManager();
		HttpConnectionManagerParams connParams = new HttpConnectionManagerParams();
		connParams.setConnectionTimeout(JCCTraySettings.getInstance().getInt(ISettingsConstants.HTTP_TIMEOUT));
		connParams.setSoTimeout(JCCTraySettings.getInstance().getInt(ISettingsConstants.HTTP_TIMEOUT));
		connectionManager.setParams(connParams);
		HttpClient client = new HttpClient(connectionManager);

		client.getParams().setAuthenticationPreemptive(true);
		if (!StringUtils.isEmptyOrNull(host.getHostName()) && !StringUtils.isEmptyOrNull(host.getPassword())){
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(host.getUsername(), host.getPassword());
			client.getState().setCredentials(new AuthScope(null, -1, null, null), credentials);
		}

		return client;
	}

	public void forceBuild(DashBoardProject project) throws Exception {
		HttpMethod method = httpMethod(project);
		try {
			if (executeMethod(method, project.getHost()) != HttpStatus.SC_OK)
				throw new Exception("There was an http error connecting to the server at "
						+ project.getHost().getHostName());
			if (!isInvokeSuccessful(method, project))
				throw new Exception(
						"The force build was not successful, the server did not return what JCCTray was expecting.");
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * Returns a {@link GetMethod} with some configuration set. Clients may
	 * override. Calls {@link #configureMethod(HttpMethod, DashBoardProject)} to
	 * configure the {@link HttpMethod} for the particular project.
	 * 
	 * @param project
	 *            the project used to configure the {@link HttpMethod}
	 * @return a {@link GetMethod}
	 */
	protected HttpMethod httpMethod(DashBoardProject project) {
		HttpMethod method = new GetMethod(forceBuildURL(project));
		configureMethod(method, project);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		return method;
	}

	protected void configureMethod(HttpMethod method, DashBoardProject project) {
		// do nothing
	}

	private boolean isInvokeSuccessful(HttpMethod method, DashBoardProject project) throws InvocationException {
		try {
			String message = getSuccessMessage(project);
			boolean invokeSuccessful = (method.getResponseBodyAsString().indexOf(message) > -1);
			if (!invokeSuccessful)
				getLog().error("Could not find the string '" + message + "' in the page located at " + method.getURI());
			return invokeSuccessful;
		} catch (IOException e) {
			getLog().error("Attempted to force the build, but the force was not successful", e);
			throw new InvocationException("Attempted to force the build, but the force was not successful", e);
		}
	}

	protected abstract String getSuccessMessage(DashBoardProject project);

	private int executeMethod(HttpMethod method, Host host) throws HTTPErrorException {
		try {
			int httpStatus = getClient(host).executeMethod(method);
			if (httpStatus != HttpStatus.SC_OK)
				getLog().error("Method failed: " + method.getStatusLine());
			return httpStatus;
		} catch (Exception e) {
			getLog()
					.error(
							"Could not force a build on project, either the webpage is not available, or there was a timeout in connecting to the cruise server.",
							e);
			throw new HTTPErrorException(
					"Could not force a build on project, either the webpage is not available, or there was a timeout in connecting to the cruise server.",
					e);
		}
	}

	protected abstract String forceBuildURL(DashBoardProject project);

	public DashBoardProjects getProjects(Host host) throws Exception {
		DashBoardProjects projects = DashboardXmlParser.getProjects(getXmlReportURL(host), getClient(host));
		for (int i = 0; i < projects.count(); i++)
			projects.getProject(i).setHost(host);
		return projects;
	}

	protected abstract String getXmlReportURL(Host host);

	protected Logger getLog() {
		return Logger.getLogger(getClass());
	}
}