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
public class HudsonTest extends TestCase {

	private Hudson	hudson;

	public void testFormatsDate() throws Exception {
		String formattedDate = hudson.formatDate("2007-07-01T10:14:34", null);
		assertEquals("10:14:34 AM, 01 Jul", formattedDate);
	}

	public void testInvalidDateReturnsSameDate() throws Exception {
		String invalidDate = "xyz";
		String formattedDate = hudson.formatDate(invalidDate, null);
		assertEquals(invalidDate, formattedDate);
	}
	
	public void testGetsForceBuildPortWhenSystemPropertyIsSet() throws Exception {
		System.setProperty("forcebuild.myCruiseServer.port", "1000");
		assertEquals("1000", hudson.getForceBuildPort(new Host("myCruiseServer", "my.ip.add.ress")));
	}
	
	public void testGetsForceBuildURLWhenPortIsSet() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name", hudson));
		String forceBuildURL = hudson.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name:80/job/myProject/build?delay=0sec", forceBuildURL);
	}
	
	public void testGetsForceBuildURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name", hudson));
		String forceBuildURL = hudson.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name:80/job/myProject/build?delay=0sec", forceBuildURL);
	}
	
	public void testGetsForceBuildURLWithTrailingSlashInHostURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name/myHudson//", hudson));
		String forceBuildURL = hudson.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name:80/myHudson/job/myProject/build?delay=0sec", forceBuildURL);
	}
	
	public void testGetXmlReportURL() throws Exception {
		assertEquals("http://host.name/hudson/cc.xml",hudson.getXmlReportURL(new Host("blah", "http://host.name/hudson//"))); 
	}
	
	public void testGetsName() throws Exception {
		assertEquals("Hudson",hudson.getName());
	}
	
	public void testGetsSuccessMessage() throws Exception {
		assertEquals("Invocation successful",hudson.getSuccessMessage(null));
	}
	
	protected void setUp() throws Exception {
		hudson = new Hudson();
	}
}
