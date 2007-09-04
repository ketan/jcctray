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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.sourceforge.jcctray.model.IJCCTraySettings;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

/**
 * The About Dialog.
 * 
 * @author Ketan Padegaonkar
 */
public class AboutDialog extends Dialog {
	/**
	 * @author Ketan Padegaonkar
	 *
	 */
	private final class LinkOpener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			new Browser(traySettings).open(e.text);
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);				
		}
	}

	private static final int		VIEW_LICENSE_ID	= 100;

	private static final Logger		log				= Logger.getLogger(AboutDialog.class);
	private final IJCCTraySettings	traySettings;

	public AboutDialog(Shell shell, IJCCTraySettings traySettings) {
		super(shell);
		this.traySettings = traySettings;
	}

	protected Control createDialogArea(Composite parent) {
		Composite contents = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		contents.setLayout(layout);

		Link link = new Link(contents, SWT.NONE);
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("version.properties"));
		} catch (Exception e) {
			log.error("Could not load version.properties", e);
		}
		
		link.setText("JCCTray\n" + 
				"\n" + 
				"Version: " + properties.getProperty("jcctray.version", "") + "\n" + 
				"(c) Copyright Ketan Padegaonkar 2007.\n" + 
				"Visit <a href=\"http://jcctray.sourceforge.net\">http://jcctray.sourceforge.net</a>\n" + 
				"\n" + 
				"This product includes software developed by\n" +
				"the Eclipse Foundation <a href=\"http://eclipse.org/\">http://eclipse.org/</a> and by\n" +
				"the Apache Software Foundation <a href=\"http://www.apache.org/\">http://www.apache.org/</a>");
		link.addSelectionListener(new LinkOpener());
		return contents;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "&Ok", true);
		createButton(parent, VIEW_LICENSE_ID, "&License", false);

		getButton(IDialogConstants.OK_ID).setFocus();
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == VIEW_LICENSE_ID) {
			try {
				new Browser(traySettings).open(new File(".").getCanonicalPath() + File.separator + "LICENSE.html");
			} catch (IOException e) {
				log.error("Could not find path to the license html.", e);
			}

		}
		super.buttonPressed(buttonId);
	}
}