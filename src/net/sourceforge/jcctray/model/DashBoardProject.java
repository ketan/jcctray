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


public class DashBoardProject {

	private String	name;
	private String	activity;
	private String	lastBuildStatus;
	private String	lastBuildLabel;
	private String	lastBuildTime;
	private String	nextBuildTime;
	private String	webUrl;
	private String	category;
	private boolean	enabled;
	private Host	host;

	public DashBoardProject() {
		this("", "", "", "", "", "", "", "");
	}

	public DashBoardProject(String name, String activity, String lastBuildStatus, String lastBuildLabel,
			String lastBuildTime, String nextBuildTime, String webUrl, String category) {
		this.name = name;
		this.activity = activity;
		this.lastBuildStatus = lastBuildStatus;
		this.lastBuildLabel = lastBuildLabel;
		this.lastBuildTime = lastBuildTime;
		this.nextBuildTime = nextBuildTime;
		this.webUrl = webUrl;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getLastBuildStatus() {
		return lastBuildStatus;
	}

	public void setLastBuildStatus(String lastBuildStatus) {
		this.lastBuildStatus = lastBuildStatus;
	}

	public String getLastBuildLabel() {
		return lastBuildLabel;
	}

	public void setLastBuildLabel(String lastBuildLabel) {
		this.lastBuildLabel = lastBuildLabel;
	}

	public String getLastBuildTime() {
		return lastBuildTime;
	}

	public void setLastBuildTime(String lastBuildTime) {
		this.lastBuildTime = lastBuildTime;
	}

	public String getNextBuildTime() {
		return nextBuildTime;
	}

	public void setNextBuildTime(String nextBuildTime) {
		this.nextBuildTime = nextBuildTime;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DashBoardProject other = (DashBoardProject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString() {
		return getName();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public void forceBuild() throws Exception {
		getHost().getCruise().forceBuild(this);
	}
}
