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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of {@link DashBoardProject}s.
 * 
 * @author Ketan Padegaonkar
 */
public class DashBoardProjects implements Iterable {

	private List	projectList	= new ArrayList();

	public void add(DashBoardProject project) {
		projectList.remove(project); // we're not a set - want to maintain
										// order and be called a set
		projectList.add(project);
	}

	public int count() {
		return projectList.size();
	}

	public DashBoardProject getProject(int index) {
		return (DashBoardProject) projectList.get(index);
	}

	public DashBoardProject[] toArray() {
		DashBoardProject[] projects = new DashBoardProject[count()];
		if (count() != 0)
			this.projectList.toArray(projects);
		return projects;
	}

	public void add(DashBoardProjects projects) {
		for (Iterator iterator = projects.iterator(); iterator.hasNext();) {
			add((DashBoardProject) iterator.next());
		}

	}

	public String toString() {
		return "Projects - " + projectList;
	}

	public Iterator iterator() {
		return projectList.iterator();
	}

	/**
	 * Returns a project which is "same as" the given project. Note: "same as" is defined
	 * as equal name and host, so the project may have a different status.
	 * 
	 * @param newProject
	 *            A project to find
	 * @return A project with the same name and host, or null.
	 */
	public DashBoardProject get(DashBoardProject newProject) {
		Iterator it = projectList.iterator();
		while (it.hasNext())
		{
			Object project = it.next();
			if (project.equals(newProject)) {
				return (DashBoardProject) project;
			}
		}
		return null;
	}

}
