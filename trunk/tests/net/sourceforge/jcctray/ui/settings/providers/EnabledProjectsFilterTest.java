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
package net.sourceforge.jcctray.ui.settings.providers;

import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.Host;
import net.sourceforge.jcctray.model.IJCCTraySettings;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author Ketan Padegaonkar
 */
public class EnabledProjectsFilterTest extends MockObjectTestCase {

	private DashBoardProject	project;
	private Host				host;
	private Mock	traySettingsMock;

	protected void setUp() throws Exception {
		host = new Host("myHostName", "my.host.name");
		project = new DashBoardProject("projectName", host);
		host.addConfiguredProject(project);
		traySettingsMock = mock(IJCCTraySettings.class);
	}

	public void testDoesNotFiltersEnabledProjectInSettings() throws Exception {
		Mock traySettingsMock = mock(IJCCTraySettings.class);
		project.setEnabled(true);
		traySettingsMock.expects(once()).method("findHostByString").with(same(host.getHostString())).will(returnValue(host));
		EnabledProjectsFilter filter = new EnabledProjectsFilter((IJCCTraySettings) traySettingsMock.proxy());
		assertTrue(filter.select(null, null, project));
	}
	
	public void testFiltersDisabledProjectInSettings() throws Exception {
		project.setEnabled(false);
		traySettingsMock.expects(once()).method("findHostByString").with(same(host.getHostString())).will(returnValue(host));
		EnabledProjectsFilter filter = new EnabledProjectsFilter((IJCCTraySettings) traySettingsMock.proxy());
		assertFalse(filter.select(null, null, project));
	}
	
	
	public void testFiltersProjectNotInSettings() throws Exception {
		host.removeConfiguredProject(project);
		traySettingsMock.expects(once()).method("findHostByString").with(same(host.getHostString())).will(returnValue(host));
		EnabledProjectsFilter filter = new EnabledProjectsFilter((IJCCTraySettings) traySettingsMock.proxy());
		assertFalse(filter.select(null, null, project));
	}
	
	public void testFiltersObjectsThatAreNotProjects() throws Exception {
		EnabledProjectsFilter filter = new EnabledProjectsFilter(null);
		assertFalse(filter.select(null, null, "SomeObject"));
	}
	
	protected void tearDown() throws Exception {
		traySettingsMock.verify();
	}
}
