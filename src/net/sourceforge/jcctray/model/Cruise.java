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


/**
 * An implementation of {@link ICruise} that connects to ThoughtWorks Cruise
 * (<a href="http://studios.thoughtworks.com/cruise">http://studios.thoughtworks.com/cruise</a>)
 * 
 * @author Ketan Padegaonkar
 */
public class Cruise extends CruiseControlJava implements ICruise {


	public String getName() {
		return "ThoughtWorks Cruise";
	}

	protected String getXmlReportURL(Host host) {
		return host.getHostName().replaceAll("/*$", "") + "/cruise/cctray.xml";
	}

}
