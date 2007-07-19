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
public class CCNetTest extends TestCase {

	private CCNet	cruiseControlNet;
	public void testFormatsDate() throws Exception {
		String formattedDate;
		
		formattedDate = cruiseControlNet.formatDate("2007-06-22T11:30:28.82815+05:30");
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
		
		formattedDate = cruiseControlNet.formatDate("2007-06-22T11:30:28.8281+05:30");
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
		
		formattedDate = cruiseControlNet.formatDate("2007-06-22T11:30:28.82+05:30");
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
		
		formattedDate = cruiseControlNet.formatDate("2007-06-22T11:30:28.82+05:30");
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
		
		formattedDate = cruiseControlNet.formatDate("2007-06-22T11:30:28+05:30");
		assertEquals("11:30:28 AM, 22 Jun", formattedDate);
	}

	public void testInvalidDateReturnsSameDate() throws Exception {
		String invalidDate = "xyz";
		String formattedDate = cruiseControlNet.formatDate(invalidDate);
		assertEquals(invalidDate, formattedDate);
	}
	
	public void testGetsForceBuildURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name", cruiseControlNet));
		String forceBuildURL = cruiseControlNet.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name/ViewFarmReport.aspx", forceBuildURL);
	}
	
	public void testGetsForceBuildURLWithTrailingSlashInHostURL() throws Exception {
		DashBoardProject dashBoardProject = new DashBoardProject("myProject", new Host("myHost", "http://my.host.name/myCruise//", cruiseControlNet));
		String forceBuildURL = cruiseControlNet.forceBuildURL(dashBoardProject);
		assertEquals("http://my.host.name/myCruise/ViewFarmReport.aspx", forceBuildURL);
	}
	
	protected void setUp() throws Exception {
		cruiseControlNet = new CCNet();
	}
}
