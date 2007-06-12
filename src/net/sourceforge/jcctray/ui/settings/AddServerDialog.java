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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.sourceforge.jcctray.model.JCCTraySettings;
import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.DashboardXmlParser;
import net.sourceforge.jcctray.model.Host;
import net.sourceforge.jcctray.ui.Utils;

public class AddServerDialog {

	private final Shell	parentShell;
	private Shell		shell;
	private Button		okButton;
	private Button		cancelButton;
	private Text		hostStringText;
	private Text		serverURLString;

	public AddServerDialog(Shell shell) {
		this.parentShell = shell;
		initialize();
		hookEvents();
	}

	private void initialize() {
		shell = new Shell(parentShell, SWT.DIALOG_TRIM);
		shell.setText("Add Server");
		shell.setSize(400, 200);
		shell.setLayout(new GridLayout(2, true));

		new Label(shell, SWT.NONE).setText("Server Name:");
		hostStringText = new Text(shell, SWT.NONE);

		new Label(shell, SWT.NONE).setText("Server URL:");
		serverURLString = new Text(shell, SWT.NONE);
		serverURLString.setText("http://localhost/ccnet");

		okButton = new Button(shell, SWT.NONE);
		okButton.setText("&Ok");

		cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setText("&Cancel");
	}

	private void hookEvents() {
		okButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				Host host = new Host(hostStringText.getText(), serverURLString.getText());
				JCCTraySettings.getInstance().addHost(host);
				try {
					DashBoardProject[] projects = DashboardXmlParser.getProjects(host.getHostName()).getProjects();
					for (int i = 0; i < projects.length; i++) {
						DashBoardProject dashBoardProject = projects[i];
						host.addProject(dashBoardProject);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Utils.saveSettings(shell);
				shell.close();
			}

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});

		cancelButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				shell.close();
			}

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
	}

	public void open() {
		shell.open();
	}

}
