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

import net.sourceforge.jcctray.model.IJCCTraySettings;
import net.sourceforge.jcctray.model.ISettingsConstants;
import net.sourceforge.jcctray.ui.Utils;

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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * A tab that shows some general settings.
 * 
 * @author Ketan Padegaonkar
 */
public class GeneralSettingsTab {

	private final TabFolder			tabFolder;
	private final IJCCTraySettings	traySettings;

	private final class OKButtonListener implements SelectionListener {
		private final Text		browserPath;
		private final Spinner	pollIntervalSpinner;
		private final Spinner	timeoutSpinner;

		private OKButtonListener(Spinner pollIntervalSpinner, Text browserPath, Spinner timeoutSpinner) {
			this.pollIntervalSpinner = pollIntervalSpinner;
			this.browserPath = browserPath;
			this.timeoutSpinner = timeoutSpinner;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			applySettings(browserPath.getText(), pollIntervalSpinner.getSelection(), timeoutSpinner.getSelection());
			((Control) e.widget).getShell().close();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	private final class ApplyButtonListener implements SelectionListener {
		private final Text		browserPath;
		private final Spinner	pollIntervalSpinner;
		private final Spinner	timeoutSpinner;

		private ApplyButtonListener(Text browserPath, Spinner pollIntervalSpinner, Spinner timeoutSpinner) {
			this.browserPath = browserPath;
			this.pollIntervalSpinner = pollIntervalSpinner;
			this.timeoutSpinner = timeoutSpinner;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			applySettings(browserPath.getText(), pollIntervalSpinner.getSelection(), timeoutSpinner.getSelection());
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
		private final Text		browserPath;
		private final Spinner	pollIntervalSpinner;
		private final Spinner	timeoutSpinner;

		private RestoreButtonListener(Text browserPath, Spinner pollIntervalSpinner, Spinner timeoutSpinner) {
			this.browserPath = browserPath;
			this.pollIntervalSpinner = pollIntervalSpinner;
			this.timeoutSpinner = timeoutSpinner;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			browserPath.setText(traySettings.get(ISettingsConstants.BROWSER_PATH));
			pollIntervalSpinner.setSelection(traySettings.getInt(ISettingsConstants.POLL_INTERVAL));
			timeoutSpinner.setSelection(traySettings.getInt(ISettingsConstants.HTTP_TIMEOUT));
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
	}

	public GeneralSettingsTab(TabFolder tabFolder, IJCCTraySettings traySettings) {
		this.tabFolder = tabFolder;
		this.traySettings = traySettings;
		createGeneralSettingsTab(tabFolder);
	}

	private void createGeneralSettingsTab(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("&General Settings");
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(new GridLayout(4, false));
		// browser settings
		new Label(composite, SWT.NONE).setText("&Browser:");
		final Text browserPath = new Text(composite, SWT.NONE);
		browserPath.setText(traySettings.get(ISettingsConstants.BROWSER_PATH));
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
		Spinner spinner = new Spinner(composite, SWT.NONE);
		spinner.setMinimum(1);
		spinner.setMaximum(3600);
		spinner.setSelection(traySettings.getInt(ISettingsConstants.POLL_INTERVAL));
		// just a filler
		new Label(composite, SWT.NONE).setText("Seconds");
		new Label(composite, SWT.NONE);

		// http timeout
		new Label(composite, SWT.NONE).setText("&HTTP Timeout:");
		Spinner timeoutSpinner = new Spinner(composite, SWT.NONE);
		timeoutSpinner.setMinimum(1);
		timeoutSpinner.setMaximum(300);
		timeoutSpinner.setSelection(traySettings.getInt(ISettingsConstants.HTTP_TIMEOUT));
		// just a filler
		new Label(composite, SWT.NONE).setText("Seconds");
		new Label(composite, SWT.NONE);

		
		// restore, cancel, apply, ok buttons

		Button restoreButton = new Button(composite, SWT.NONE);
		restoreButton.setText("&Restore");
		restoreButton.addSelectionListener(new RestoreButtonListener(browserPath, spinner, timeoutSpinner));

		Button cancelButton = new Button(composite, SWT.NONE);
		cancelButton.setText("&Cancel");
		cancelButton.addSelectionListener(new CancelButtonListener());

		Button applyButton = new Button(composite, SWT.NONE);
		applyButton.setText("&Apply");
		applyButton.addSelectionListener(new ApplyButtonListener(browserPath, spinner, timeoutSpinner));

		Button okButton = new Button(composite, SWT.NONE);
		okButton.setText("&Ok");
		okButton.addSelectionListener(new OKButtonListener(spinner, browserPath, timeoutSpinner));
	}

	private String openFileDialog(Shell shell) {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText("Browser executable");
		if (SWT.getPlatform().equals("win32"))
			fileDialog.setFilterExtensions(new String[] { ".exe" });
		return fileDialog.open();
	}

	private void applySettings(String browserPath, int pollInterval, int httpTimeout) {
		traySettings.set(ISettingsConstants.BROWSER_PATH, browserPath);
		traySettings.set(ISettingsConstants.POLL_INTERVAL, "" + pollInterval);
		traySettings.set(ISettingsConstants.HTTP_TIMEOUT, "" + httpTimeout);
		Utils.saveSettings(tabFolder.getShell());
	}

}
