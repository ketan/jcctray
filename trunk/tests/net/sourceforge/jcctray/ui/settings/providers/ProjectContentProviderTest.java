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
import net.sourceforge.jcctray.model.DashBoardProjects;
import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar
 */
public class ProjectContentProviderTest extends TestCase {

	public void testGetsProjectsAsArray() throws Exception {
		DashBoardProjects projects = new DashBoardProjects();
		projects.add(new DashBoardProject("a"));
		projects.add(new DashBoardProject("b"));
		projects.add(new DashBoardProject("c"));
		Object[] elements = new ProjectContentProvider().getElements(projects);
		assertEquals(3, elements.length);
	}
	
	public void testGetsAnythingElseAsNull() throws Exception {
		assertNull(new ProjectContentProvider().getElements("asdf"));
	}
}
