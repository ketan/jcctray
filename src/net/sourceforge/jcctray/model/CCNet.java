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

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * @author Ketan Padegaonkar
 */
public class CCNet implements ICruise {

	private static final Logger	log	= Logger.getLogger(CCNet.class);

	private static HttpClient	client;

	public void forceBuild(DashBoardProject project) {
		String string = forceBuildURL(project);
		GetMethod method = new GetMethod(string);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		int statusCode;
		try {
			statusCode = getClient().executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + method.getStatusLine());
			}
		} catch (Exception e) {
			log.error("Could not force build on project" + project, e);
		}
	}

	private String forceBuildURL(DashBoardProject project) {
		return project.getHost().getHostName()
				+ "/ViewFarmReport.aspx?forcebuild=true&forceBuildServer=local&ForceBuild=Force&forceBuildProject="
				+ project.getName();
	}

	public void openBrowser(DashBoardProject project) {
		// TODO Auto-generated method stub

	}

	private static HttpClient getClient() {
		if (client == null) {
			client = new HttpClient();
			client.setConnectionTimeout(10000);
			client.setTimeout(10000);
		}
		return client;
	}
}
