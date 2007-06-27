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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.xml.sax.SAXException;

public class DashboardXmlParser {

	public static DashBoardProjects getProjects(String url, HttpClient client) throws HttpException, IOException, SAXException {
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("Could not connect to " + url + ". The server returned a " + statusCode
						+ " status code");
			}
			return getProjects(method.getResponseBodyAsStream());
		}finally{
			method.releaseConnection();	
		}
	}

	public static DashBoardProjects getProjects(Reader reader) throws IOException, SAXException {
		return (DashBoardProjects) createDigester().parse(reader);
	}

	public static DashBoardProjects getProjects(InputStream inStream) throws IOException, SAXException {
		return getProjects(new InputStreamReader(inStream));
	}

	private static Digester createDigester() {
		Digester digester = new Digester();
		digester.addRule("Projects", new ObjectCreateRule(DashBoardProjects.class));
		digester.addRule("Projects/Project", new ObjectCreateRule(DashBoardProject.class));
		digester.addRule("Projects/Project", new SetPropertiesRule());
		digester.addRule("Projects/Project", new SetNextRule("add"));
		return digester;
	}
}
