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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Ketan Padegaonkar
 *
 */
public interface IProjectLabelConstants {

	public static final String	WAITING_FOR_BUILD		= "Waiting for next build to happen";
	public static final String	UNKNOWN					= "Unknown";
	public static final String	CHECKING_MODIFICATIONS	= "CheckingModifications";
	public static final String	BUILDING				= "Building";
	public static final String	SLEEPING				= "Sleeping";
	public static final String	FAILURE					= "Failure";
	public static final String	SUCCESS					= "Success";
	public static final Image	GRAY_IMG				= new Image(Display.getDefault(), ProjectLabelProvider.class
																.getClassLoader().getResourceAsStream("icons/Gray.png"));
	public static final Image	GREEN_IMG				= new Image(Display.getDefault(), ProjectLabelProvider.class
																.getClassLoader()
																.getResourceAsStream("icons/Green.png"));
	public static final Image	ORANGE_IMG				= new Image(Display.getDefault(), ProjectLabelProvider.class
																.getClassLoader().getResourceAsStream(
																		"icons/Orange.png"));
	public static final Image	RED_IMG					= new Image(Display.getDefault(), ProjectLabelProvider.class
																.getClassLoader().getResourceAsStream("icons/Red.png"));
	public static final Image	YELLOW_IMG				= new Image(Display.getDefault(), ProjectLabelProvider.class
																.getClassLoader().getResourceAsStream(
																		"icons/Yellow.png"));

}