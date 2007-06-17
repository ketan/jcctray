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
import java.util.List;

public class DashBoardProjects {

	private List	projectList	= new ArrayList();

	public void add(DashBoardProject project) {
		projectList.remove(project); // we're not a set - want to maintain order and be called a set
		projectList.add(project);
	}

	public int count() {
		return projectList.size();
	}

	public DashBoardProject getProject(int index) {
		return (DashBoardProject) projectList.get(index);
	}

	public DashBoardProject[] getProjects() {
		DashBoardProject[] projects = new DashBoardProject[count()];
		if (count() != 0)
			this.projectList.toArray(projects);
		return projects;

	}

	public void add(DashBoardProjects projects) {
		projectList.addAll(projects.projectList);
	}

	public String toString() {
		return "Projects - " + projectList;
	}
}
