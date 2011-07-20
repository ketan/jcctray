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

import org.apache.log4j.Logger;

/**
 * Represents a host that runs CruiseControl.
 * 
 * @author Ketan Padegaonkar
 */
public class Host {
	private static final Logger	log	= Logger.getLogger(Host.class);
	public String				hostName;
	public String				hostString;
	private HashMap				configuredProjects;
	private ICruise				cruise;
	private String 				username;
	private String 				password;

	public Host() {
		this("", "");
	}

	public Host(String hostString, String hostName, ICruise cruise) {
		this(hostString, hostName, cruise, null, null);
	}

	public Host(String hostString, String hostName, ICruise cruise, String username, String password) {
		this.hostString = hostString;
		this.hostName = hostName;
		this.cruise = cruise;
		this.username = username;
		this.password = password;
		this.configuredProjects = new HashMap();
	}

	public Host(String hostString, String hostName) {
		this(hostString, hostName, new DefaultCruise());
	}

	public Host(String hostString, String hostName, String username, String password) {
		this(hostString, hostName, new DefaultCruise(), username, password);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((hostString == null) ? 0 : hostString.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Host other = (Host) obj;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public void addConfiguredProject(DashBoardProject project) {
		configuredProjects.put(project.getName(), project);
		project.setHost(this);
	}

	public int configuredProjectCount() {
		return configuredProjects.size();
	}

	public Collection getConfiguredProjects() {
		return configuredProjects.values();
	}

	public DashBoardProjects getCruiseProjects() throws Exception {
		return getCruise().getProjects(this);
	}

	public DashBoardProject getConfiguredProject(String projectName) {
		return (DashBoardProject) configuredProjects.get(projectName);
	}

	public String toString() {
		return getHostString() + " - " + getHostName();
	}

	public ICruise getCruise() {
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
			log.error("Could not instantiate class: " + cruiseClassName, e);
		}
	}

	public String getCruiseClass() {
		return getCruise().getClass().getName();
	}

	public void setProjects(HashMap projects) {
		this.configuredProjects = projects;
	}

	public void removeConfiguredProject(DashBoardProject project) {
		configuredProjects.remove(project.getName());
	}

	public void forceBuild(DashBoardProject project) throws Exception {
		getCruise().forceBuild(project);
	}

	public Integer getForceBuildPort() {
		return Integer.getInteger("forcebuild." + getHostString() + ".port", 8000);
	}

}
