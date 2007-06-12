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
package net.sourceforge.jcctray.ui;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.sourceforge.jcctray.model.JCCTraySettings;

public class Utils {
	private static final Logger	log	= Logger.getLogger(Utils.class);

	public static void saveSettings(Shell shell) {
		try {
			JCCTraySettings.getInstance().save();
		} catch (Exception ex) {
			log.error("could not save settings", ex);
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage("Could not save the cctray settings");
			messageBox.setText("Error saving settings");
			messageBox.open();
		}
	}
}
