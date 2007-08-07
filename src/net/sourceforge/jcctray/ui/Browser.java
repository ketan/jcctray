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

import java.io.File;
import java.io.IOException;

import net.sourceforge.jcctray.model.IJCCTraySettings;
import net.sourceforge.jcctray.model.ISettingsConstants;

import org.apache.log4j.Logger;
import org.eclipse.swt.program.Program;

/**
 * Represents a web browser. Automagically detects whether there is a default
 * browser set in the settings, if not uses the default OS browser.
 * 
 * @author Ketan Padegaonkar
 */
public class Browser {
	private static final Logger		log	= Logger.getLogger(Browser.class);
	private final IJCCTraySettings	traySettings;

	public Browser(IJCCTraySettings traySettings) {
		this.traySettings = traySettings;
	}

	public void open(String url) {
		String browserPath = traySettings.get(ISettingsConstants.BROWSER_PATH);
		if (browserPath != null && new File(browserPath).exists() && new File(browserPath).isFile())
			try {
				exec(browserPath + " " + url);
			} catch (IOException e) {
				log.error("Could not open browser: " + browserPath, e);
				launch(url);
			}
		else
			launch(url);
	}

	protected void launch(String url) {
		Program.launch(url);
	}

	protected void exec(String string) throws IOException {
		Runtime.getRuntime().exec(string);
	}
}
