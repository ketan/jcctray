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
package net.sourceforge.jcctray.ui.settings.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import net.sourceforge.jcctray.model.JCCTraySettings;
import net.sourceforge.jcctray.model.ISettingsConstants;
import net.sourceforge.jcctray.ui.Utils;

public class GeneralSettingsTab {

	private final TabFolder	tabFolder;

	private final class OKButtonListener implements SelectionListener {
		private final Text	pollIntervalText;
		private final Text	browserPath;

		private OKButtonListener(Text pollIntervalText, Text browserPath) {
			this.pollIntervalText = pollIntervalText;
			this.browserPath = browserPath;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			applySettings(pollIntervalText.getText(), browserPath.getText());
			((Control) e.widget).getShell().close();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	private final class ApplyButtonListener implements SelectionListener {
		private final Text	browserPath;
		private final Text	pollIntervalText;

		private ApplyButtonListener(Text browserPath, Text pollIntervalText) {
			this.browserPath = browserPath;
			this.pollIntervalText = pollIntervalText;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			applySettings(pollIntervalText.getText(), browserPath.getText());
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	private final class CancelButtonListener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			((Control) e.widget).getShell().close();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	private final class RestoreButtonListener implements SelectionListener {
		private final Text	browserPath;
		private final Text	pollIntervalText;

		private RestoreButtonListener(Text browserPath, Text pollIntervalText) {
			this.browserPath = browserPath;
			this.pollIntervalText = pollIntervalText;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			browserPath.setText(JCCTraySettings.getInstance().get(ISettingsConstants.BROWSER_PATH));
			pollIntervalText.setText("" + JCCTraySettings.getInstance().getInt(ISettingsConstants.POLL_INTERVAL));
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	public GeneralSettingsTab(TabFolder tabFolder) {
		this.tabFolder = tabFolder;
		createGeneralSettingsTab(tabFolder);
	}

	private void createGeneralSettingsTab(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("General Settings");
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(new GridLayout(4, false));
		// browser settings
		new Label(composite, SWT.NONE).setText("&Browser:");
		final Text browserPath = new Text(composite, SWT.NONE);
		browserPath.setText(JCCTraySettings.getInstance().get(ISettingsConstants.BROWSER_PATH));
		browserPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button browserPathButton = new Button(composite, SWT.NONE);
		browserPathButton.setText("&Browse");

		browserPathButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				browserPath.setText(openFileDialog(((Control) e.widget).getShell()));
			}

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		new Label(composite, SWT.NONE);
		// poll interval settings

		new Label(composite, SWT.NONE).setText("&Poll Interval:");
		final Text pollIntervalText = new Text(composite, SWT.NONE);
		pollIntervalText.setText("" + JCCTraySettings.getInstance().getInt(ISettingsConstants.POLL_INTERVAL));
		// just a filler
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		// restore, cancel, apply, ok buttons

		Button restoreButton = new Button(composite, SWT.NONE);
		restoreButton.setText("&Restore");
		restoreButton.addSelectionListener(new RestoreButtonListener(browserPath, pollIntervalText));

		Button cancelButton = new Button(composite, SWT.NONE);
		cancelButton.setText("&Cancel");
		cancelButton.addSelectionListener(new CancelButtonListener());

		Button applyButton = new Button(composite, SWT.NONE);
		applyButton.setText("&Apply");
		applyButton.addSelectionListener(new ApplyButtonListener(browserPath, pollIntervalText));

		Button okButton = new Button(composite, SWT.NONE);
		okButton.setText("&Ok");
		okButton.addSelectionListener(new OKButtonListener(pollIntervalText, browserPath));
	}

	private String openFileDialog(Shell shell) {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText("Browser executable");
		if (SWT.getPlatform().equals("win32"))
			fileDialog.setFilterExtensions(new String[] { ".exe" });
		return fileDialog.open();
	}

	private void applySettings(String pollInterval, String browserPath) {
		JCCTraySettings.getInstance().set(ISettingsConstants.BROWSER_PATH, browserPath);
		JCCTraySettings.getInstance().set(ISettingsConstants.POLL_INTERVAL, pollInterval);
		Utils.saveSettings(tabFolder.getShell());
	}

}
