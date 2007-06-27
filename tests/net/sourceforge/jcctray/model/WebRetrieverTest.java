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

import java.io.StringReader;

import junit.framework.TestCase;

public class WebRetrieverTest extends TestCase {

	public void testGetsTwoProjects() throws Exception {
		String projectXml = "<Projects>" + "<Project name=\"p1\" />" + "<Project name=\"p2\"/>" + "</Projects>";
		StringReader stringReader = new StringReader(projectXml);
		DashBoardProjects projects = DashboardXmlParser.getProjects(stringReader);
		assertEquals(2, projects.count());
	}

	public void testGetsProjectProperties() throws Exception {
		String projectXml = "<Projects>" + "<Project name=\"Brighton\" " + "category=\"Blah\" "
				+ "activity=\"Sleeping\" " + "lastBuildStatus=\"Success\" " + "lastBuildLabel=\"V0.0.0.unknown\" "
				+ "lastBuildTime=\"2007-05-09T06:57:56.2584028-04:00\" "
				+ "nextBuildTime=\"2007-05-10T06:29:26.5109320-04:00\" "
				+ "webUrl=\"http://renogold/ccnet/server/local/project/Brighton/ViewProjectReport.aspx\" />"
				+ "</Projects>";

		StringReader stringReader = new StringReader(projectXml);
		DashBoardProjects projects = DashboardXmlParser.getProjects(stringReader);

		DashBoardProject project = projects.getProject(0);

		assertEquals(project.getName(), "Brighton");
		assertEquals(project.getCategory(), "Blah");
		assertEquals(project.getActivity(), "Sleeping");
		assertEquals(project.getLastBuildStatus(), "Success");
		assertEquals(project.getLastBuildLabel(), "V0.0.0.unknown");
		assertEquals(project.getLastBuildTime(), "2007-05-09T06:57:56.2584028-04:00");
		assertEquals(project.getNextBuildTime(), "2007-05-10T06:29:26.5109320-04:00");
		assertEquals(project.getWebUrl(), "http://renogold/ccnet/server/local/project/Brighton/ViewProjectReport.aspx");
	}
}
