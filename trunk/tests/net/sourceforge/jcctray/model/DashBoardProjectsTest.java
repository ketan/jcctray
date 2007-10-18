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

public class DashBoardProjectsTest extends TestCase {

	public void testAddsTwoDashBoardProjects() throws Exception {
		DashBoardProjects projects1 = new DashBoardProjects();
		projects1.add(new DashBoardProject("a"));
		projects1.add(new DashBoardProject("b"));
		projects1.add(new DashBoardProject("c"));

		assertEquals(3, projects1.count());
	}

	public void testAddsDashBoardProjects() throws Exception {

		DashBoardProjects projects1 = new DashBoardProjects();
		projects1.add(new DashBoardProject("a"));
		projects1.add(new DashBoardProject("b"));
		projects1.add(new DashBoardProject("c"));

		DashBoardProjects projects2 = new DashBoardProjects();
		projects2.add(projects1);

		assertEquals(3, projects2.count());
	}
}
