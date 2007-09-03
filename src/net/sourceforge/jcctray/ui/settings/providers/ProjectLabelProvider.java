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

import java.util.TimeZone;

import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.utils.StringUtils;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Provides text and images for {@link DashBoardProject} for viewers.
 * 
 * @author Ketan Padegaonkar
 */
public class ProjectLabelProvider implements ITableLabelProvider, ILabelProvider, IProjectLabelConstants {

	public Image getColumnImage(Object arg0, int column) {
		DashBoardProject project = (DashBoardProject) arg0;
		if (column == 0)
			return getImage(project);
		else
			return null;
	}

	public Image getImage(Object element) {
		DashBoardProject project = (DashBoardProject) element;
		String activity = project.getActivity();
		String lastBuildStatus = project.getLastBuildStatus();

		if (lastBuildStatus.equals(SUCCESS)) {
			if (activity.equals(BUILDING))
				return IProjectLabelConstants.YELLOW_IMG;
			else
				return IProjectLabelConstants.GREEN_IMG;
		} else if (lastBuildStatus.equals(FAILURE)) {
			if (activity.equals(BUILDING))
				return IProjectLabelConstants.ORANGE_IMG;
			else
				return IProjectLabelConstants.RED_IMG;
		} else if (activity.equals(BUILDING))
			return IProjectLabelConstants.YELLOW_IMG;
		return IProjectLabelConstants.GRAY_IMG;
	}

	public String getColumnText(Object element, int column) {
		DashBoardProject project = (DashBoardProject) element;
		String name = project.getName();
		String activity = project.getActivity();
		String nextBuildTime = project.getNextBuildTime();
		String lastBuildLabel = project.getLastBuildLabel();
		String lastBuildTime = project.getLastBuildTime();

		switch (column) {
		case 0:
			return name;
		case 1:
			return project.getHost().getHostString();
		case 2:
			return activity;
		case 3:
			String formatDate = project.getHost().getCruise().formatDate(nextBuildTime, TimeZone.getDefault());

			if (StringUtils.isEmptyOrNull(nextBuildTime) || StringUtils.isEmptyOrNull(formatDate))
				return WAITING_FOR_BUILD;

			return "Next build check at: " + formatDate;

		case 4:
			return lastBuildLabel;
		case 5:
			if (StringUtils.isEmptyOrNull(lastBuildTime))
				return "";
			return project.getHost().getCruise().formatDate(lastBuildTime, null);
		default:
			return "";
		}
	}

	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	public String getText(Object element) {
		return ((DashBoardProject) element).getName();
	}

}
