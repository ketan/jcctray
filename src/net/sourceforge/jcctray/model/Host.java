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

import java.util.Collection;
import java.util.HashMap;

public class Host {
	public String	hostName;
	public String	hostString;
	private HashMap	projects;
	private ICruise	cruise;

	public Host() {
		this("", "");
	}

	public Host(String hostString, String hostName) {
		this.hostString = hostString;
		this.hostName = hostName;
		this.projects = new HashMap();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostString() {
		return hostString;
	}

	public void setHostString(String hostString) {
		this.hostString = hostString;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((hostString == null) ? 0 : hostString.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Host other = (Host) obj;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (hostString == null) {
			if (other.hostString != null)
				return false;
		} else if (!hostString.equals(other.hostString))
			return false;
		return true;
	}

	public void addProject(DashBoardProject project) {
		projects.put(project.getName(), project);
		project.setHost(this);
	}

	public int projectCount() {
		return projects.size();
	}

	public Collection getProjects() {
		return projects.values();
	}

	public DashBoardProject getProject(String projectName) {
		return (DashBoardProject) projects.get(projectName);
	}

	public String toString() {
		return getHostString() + " - " + getHostName();
	}

	public ICruise getCruise() {
		if (this.cruise == null)
			this.cruise = new DefaultCruise();
		return cruise;
	}

	public void setCruise(ICruise cruise) {
		this.cruise = cruise;
	}

	public void setCruiseClass(String cruiseClassName) {
		try {
			Class cruiseClazz = Class.forName(cruiseClassName);
			setCruise((ICruise) cruiseClazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCruiseClass() {
		return getCruise().getClass().getName();
	}

	public void setProjects(HashMap projects) {
		this.projects = projects;
	}

}
