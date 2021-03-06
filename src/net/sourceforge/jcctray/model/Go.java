/*******************************************************************************
 *  Copyright 2009 Ketan Padegaonkar http://ketan.padegaonkar.name
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


/**
 * An implementation of {@link ICruise} that connects to ThoughtWorks Go
 * (<a href="http://studios.thoughtworks.com/">http://studios.thoughtworks.com/go</a>)
 * 
 * @author Ketan Padegaonkar
 */
public class Go extends CruiseControlJava implements ICruise {


	public String getName() {
		return "ThoughtWorks Go";
	}
	
	protected String forceBuildURL(DashBoardProject project) {
		String hostName = project.getHost().getHostName();
		URL url = null;
		try {
			url = new URL(hostName);
			return url.getProtocol() + "://" + url.getHost() + ":" + getForceBuildPort(project.getHost()) + url.getPath().replaceAll("/*$", "")
					+ "/go/api/pipelines/" + project.getName() + "/schedule";
		} catch (MalformedURLException e) {
			getLog().error("The url was malformed: " + url, e);
		}
		return null;
	}

	protected String getXmlReportURL(Host host) {
		return host.getHostName().replaceAll("/*$", "") + "/go/cctray.xml";
	}

}
