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
/**
 * 
 */
package net.sourceforge.jcctray.ui.settings.providers;

import net.sourceforge.jcctray.model.DashBoardProject;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Provides text and images for {@link DashBoardProject} for viewers.
 * 
 * @author Ketan Padegaonkar
 */
public class ProjectLabelProvider implements ITableLabelProvider, ILabelProvider {

	public static final Image	GRAY_IMG	= new Image(Display.getDefault(), ProjectLabelProvider.class
													.getClassLoader().getResourceAsStream("icons/Gray.png"));
	public static final Image	GREEN_IMG	= new Image(Display.getDefault(), ProjectLabelProvider.class
													.getClassLoader().getResourceAsStream("icons/Green.png"));
	public static final Image	ORANGE_IMG	= new Image(Display.getDefault(), ProjectLabelProvider.class
													.getClassLoader().getResourceAsStream("icons/Orange.png"));
	public static final Image	RED_IMG		= new Image(Display.getDefault(), ProjectLabelProvider.class
													.getClassLoader().getResourceAsStream("icons/Red.png"));
	public static final Image	YELLOW_IMG	= new Image(Display.getDefault(), ProjectLabelProvider.class
													.getClassLoader().getResourceAsStream("icons/Yellow.png"));

	public Image getColumnImage(Object arg0, int column) {
		DashBoardProject project = (DashBoardProject) arg0;
		switch (column) {
		case 0:
			return getImage(project);
		}
		return null;
	}

	public Image getImage(Object element) {
		DashBoardProject project = (DashBoardProject) element;
		String activity = project.getActivity();
		String lastBuildStatus = project.getLastBuildStatus();
		if (activity.equals("Sleeping") && lastBuildStatus.equals("Success"))
			return ProjectLabelProvider.GREEN_IMG;
		else if (activity.equals("Sleeping") && lastBuildStatus.equals("Failure"))
			return ProjectLabelProvider.RED_IMG;
		else if (activity.equals("Building") && lastBuildStatus.equals("Success"))
			return ProjectLabelProvider.YELLOW_IMG;
		else if (activity.equals("Building") && lastBuildStatus.equals("Failure"))
			return ProjectLabelProvider.ORANGE_IMG;
		else if (activity.equals("Building") && lastBuildStatus.equals("Unknown"))
			return ProjectLabelProvider.YELLOW_IMG;
		else if (activity.equals("CheckingModifications"))
			return ProjectLabelProvider.GRAY_IMG;
		return ProjectLabelProvider.GRAY_IMG;
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
			if (nextBuildTime == null || "".equals(nextBuildTime.trim()))
				return "";
			String formatDate = project.getHost().getCruise().formatDate(nextBuildTime);
			if (formatDate != null && !formatDate.trim().equals(""))
				return "Next build check at: " + formatDate;
			return "";
		case 4:
			return lastBuildLabel;
		case 5:
			if (lastBuildTime == null || "".equals(lastBuildTime.trim()))
				return "";
			return project.getHost().getCruise().formatDate(lastBuildTime);
		}
		return null;
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
