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

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar
 */
public class CruiseControlJava2_7Test extends TestCase {

	private CruiseControlJava2_7	cruiseControl;

	public void testFormatsDate() throws Exception {
		String formattedDate = cruiseControl.formatDate("2007-07-01T10:14:34", null);
		assertEquals("10:14:34 AM, 01 Jul", formattedDate);
	}

	public void testInvalidDateReturnsSameDate() throws Exception {
		String invalidDate = "xyz";
		String formattedDate = cruiseControl.formatDate(invalidDate, null);
		assertEquals(invalidDate, formattedDate);
	}
	
	public void testGetsForceBuildPortWhenSystemPropertyIsSet() throws Exception {
		System.setProperty("forcebuild.myCruiseServer.port", "1000");
		assertEquals("1000", cruiseControl.getForceBuildPort(new Host("myCruiseServer", "my.ip.add.ress")));
	}
	
	public void testGetsForceBuildURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name", cruiseControl));
		String forceBuildURL = cruiseControl.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name:8000/invoke?operation=build&objectname=CruiseControl+Project%3Aname%3DmyProject", forceBuildURL);
	}
	
	public void testGetsForceBuildURLWithTrailingSlashInHostURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name/myCruise//", cruiseControl));
		String forceBuildURL = cruiseControl.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name:8000/myCruise/invoke?operation=build&objectname=CruiseControl+Project%3Aname%3DmyProject", forceBuildURL);
	}
	
	public void testGetXmlReportURL() throws Exception {
		assertEquals("http://host.name/cruise/dashboard/cctray.xml",cruiseControl.getXmlReportURL(new Host("blah", "http://host.name/cruise//"))); 
	}
	
	public void testGetsName() throws Exception {
		assertEquals("CruiseControl >= V2.7",cruiseControl.getName());
	}
	
	public void testGetsSuccessMessage() throws Exception {
		assertEquals("Invocation successful",cruiseControl.getSuccessMessage(null));
	}
	
	protected void setUp() throws Exception {
		cruiseControl = new CruiseControlJava2_7();
	}
}
