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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * @author Ketan Padegaonkar
 */
public class CruiseControlJava extends HTTPCruise implements ICruise {

	protected void configureMethod(HttpMethod method, DashBoardProject project) {
		// do nothing
	}
	
	protected String forceBuildURL(DashBoardProject project) {
		String hostString = project.getHost().getHostName();
		URL url = null;
		try {
			url = new URL(hostString);
			return url.getProtocol() + "://" + url.getHost()
					+ ":8000/invoke?operation=build&objectname=CruiseControl+Project%3Aname%3D" + project.getName();
		} catch (MalformedURLException e) {
			getLog().error("The url was malformed: " + url, e);
		}
		return null;
	}
	
	public String formatDate(String date) {
		Date parse;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
			return new SimpleDateFormat("h:mm:ss a, dd MMM").format(parse);
		} catch (ParseException e) {
			getLog().error("Could not parse date: " + date);
		}
		return date;
	}
	
	public String getName() {
		return "CruiseControl";
	}

	protected String getSuccessMessage(DashBoardProject project) {
		return "Invocation successful";
	}

	protected String getXmlReportURL(Host host) {
		return host.getHostName().replaceAll("/$", "") + "/xml";
	}

	protected HttpMethod httpMethod(DashBoardProject project) {
		HttpMethod method = new GetMethod(forceBuildURL(project));
		configureMethod(method, project);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		return method;
	}
}
