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

public class HostTest extends TestCase {

	private DashBoardProject	project2;
	private DashBoardProject	project1;
	private Host				host;
	private DashBoardProject	project;

	public void testOverridesEquals() throws Exception {
		assertTrue(new Host("bar", "foo").equals(new Host("bar", "foo")));
		assertFalse(new Host("bar", "foo").equals(new Host("baz", "foo")));
	}

	public void testOverridesHashCode() throws Exception {
		assertEquals(new Host("bar", "foo").hashCode(), new Host("bar", "foo").hashCode());
		assertTrue(new Host("bar", "foo").hashCode() != new Host("baz", "foo").hashCode());
	}

	public void testAddsProjects() throws Exception {
		host.addProject(project);
		assertEquals(1, host.projectCount());
		assertEquals(project, host.getProject("myProject"));
	}

	public void testAddsMultipleProjectsToHost() throws Exception {
		host.addProject(project1);
		host.addProject(project2);
		assertEquals(2, host.projectCount());
		assertEquals(project1, host.getProject("myProject1"));
		assertEquals(project2, host.getProject("myProject2"));
	}

	public void testDoesNotAddSameProjectTwice() throws Exception {
		host.addProject(project1);
		host.addProject(project1);
		assertEquals(1, host.projectCount());
		assertEquals(project1, host.getProject("myProject1"));
	}

	protected void setUp() throws Exception {
		host = new Host("foo", "bar");
		project = new DashBoardProject("myProject", null, null, null, null, null, null, null);
		project1 = new DashBoardProject("myProject1", null, null, null, null, null, null, null);
		project2 = new DashBoardProject("myProject2", null, null, null, null, null, null, null);
	}
}
