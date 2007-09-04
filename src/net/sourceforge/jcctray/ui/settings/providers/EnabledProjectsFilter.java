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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * A filter that selects projects that have been enabled in the
 * {@link IJCCTraySettings}.
 * 
 * @see Host#getConfiguredProject(String)
 * @see DashBoardProject#isEnabled()
 * @author Ketan Padegaonkar
 */
public class EnabledProjectsFilter extends ViewerFilter {

	private final IJCCTraySettings	traySettings;

	public EnabledProjectsFilter(IJCCTraySettings traySettings) {
		this.traySettings = traySettings;
	}

	public boolean select(Viewer viewer, Object parentElement, Object aProject) {
		return select(aProject);
	}

	public boolean select(Object aProject) {
		if (aProject instanceof DashBoardProject) {
			DashBoardProject project = (DashBoardProject) aProject;
			Host host = project.getHost();
			Host hostInSettings = traySettings.findHostByString(host.getHostString());
			DashBoardProject projectInSettings = hostInSettings.getConfiguredProject(project.getName());
			return (projectInSettings != null && projectInSettings.isEnabled());
		}
		return false;
	}
}