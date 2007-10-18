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

import java.util.TimeZone;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar
 */
public class CCNetTest extends TestCase {

	private CCNet	cruiseControl;

	public void testFormatsDate() throws Exception {
		String formattedDate;

		formattedDate = cruiseControl.formatDate("2007-06-22T11:30:28.82815+05:30", TimeZone.getTimeZone("EST"));
		assertEquals("1:00:28 AM, 22 Jun", formattedDate);

		formattedDate = cruiseControl.formatDate("2007-06-22T11:30:28.8281+05:30", TimeZone.getTimeZone("EST"));
		assertEquals("1:00:28 AM, 22 Jun", formattedDate);

		formattedDate = cruiseControl.formatDate("2007-06-22T11:30:28.82+05:30", TimeZone.getTimeZone("EST"));
		assertEquals("1:00:28 AM, 22 Jun", formattedDate);

		formattedDate = cruiseControl.formatDate("2007-06-22T11:30:28.82+05:30", TimeZone.getTimeZone("EST"));
		assertEquals("1:00:28 AM, 22 Jun", formattedDate);

		formattedDate = cruiseControl.formatDate("2007-06-22T11:30:28+05:30", TimeZone.getTimeZone("IST"));
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
	}

	public void testInvalidDateReturnsSameDate() throws Exception {
		String invalidDate = "xyz";
		String formattedDate = cruiseControl.formatDate(invalidDate, null);
		assertEquals(invalidDate, formattedDate);
	}

	public void testGetsForceBuildURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name",
				cruiseControl));
		String forceBuildURL = cruiseControl.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name/ViewFarmReport.aspx", forceBuildURL);
	}

	public void testGetsForceBuildURLWithTrailingSlashInHostURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost",
				"http://my.host.name/myCruise//", cruiseControl));
		String forceBuildURL = cruiseControl.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name/myCruise/ViewFarmReport.aspx", forceBuildURL);
	}

	public void testGetXmlReportURL() throws Exception {
		assertEquals("http://host.name/cruise/XmlStatusReport.aspx",cruiseControl.getXmlReportURL(new Host("blah", "http://host.name/cruise//"))); 
	}
	
	public void testGetsName() throws Exception {
		assertEquals("CruiseControl.NET", cruiseControl.getName());
	}

	public void testGetsSuccessMessage() throws Exception {
		assertEquals("Build successfully forced for myProject", cruiseControl.getSuccessMessage(new DashBoardProject("myProject")));
	}

	protected void setUp() throws Exception {
		cruiseControl = new CCNet();
	}
}
