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
package net.sourceforge.jcctray.ui.settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

public class SettingsDialog {

	private final Shell	parentShell;
	private Shell		shell;

	public SettingsDialog(Shell shell) {
		this.parentShell = shell;
		initialize();
	}

	private void initialize() {
		createShell();
		new SettingTabs(shell);
	}

	private void createShell() {
		shell = new Shell(parentShell);
		shell.setText("JCCTray Settings");
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		shell.setLayout(fillLayout);
	}

	public void open() {
		shell.setSize(500, 300);
		shell.open();
	}

}
