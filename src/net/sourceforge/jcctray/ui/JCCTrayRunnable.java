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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TrayItem;

import net.sourceforge.jcctray.model.JCCTraySettings;
import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.DashBoardProjects;
import net.sourceforge.jcctray.model.DashboardXmlParser;
import net.sourceforge.jcctray.model.Host;

public class JCCTrayRunnable implements Runnable {

	public class ProjectsFilter extends ViewerFilter {

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof DashBoardProject) {
				try {
					DashBoardProject project = (DashBoardProject) element;
					Host host = project.getHost();
					Host hostInSettings = JCCTraySettings.getInstance().findHostByString(host.getHostString());
					DashBoardProject projectInSettings = hostInSettings.getProject(project.getName());
					if ((projectInSettings != null) && projectInSettings.isEnabled())
						return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	}

	private final Table		table;
	private TableViewer		tableViewer;
	public boolean			shouldRun	= true;
	private final TrayItem	trayItem;

	public JCCTrayRunnable(Table table, TrayItem trayItem) {
		this.trayItem = trayItem;
		tableViewer = new TableViewer(table);
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setFilters(new ViewerFilter[] { new ProjectsFilter() });
		this.table = table;
	}

	public void run() {
		while (shouldRun) {
			try {
				updateUI();
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updateTrayIcon(DashBoardProjects projects) {
		trayItem.setImage(deduceImageToSet(projects));
	}

	private Image deduceImageToSet(DashBoardProjects projects) {
		DashBoardProject[] projectList = projects.getProjects();
		Image icon = LabelProvider.GREEN_IMG;
		for (int i = 0; i < projectList.length; i++) {
			Image projectIcon = LabelProvider.getIcon(projectList[i]);
			if (projectIcon == LabelProvider.RED_IMG)
				icon = LabelProvider.RED_IMG;
			if (projectIcon == LabelProvider.YELLOW_IMG)
				icon = LabelProvider.YELLOW_IMG;
			if (projectIcon == LabelProvider.ORANGE_IMG)
				icon = LabelProvider.ORANGE_IMG;
		}
		return icon;
	}

	private void updateUI() {
		table.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!table.isDisposed()) {
					DashBoardProjects projects = new DashBoardProjects();
					Collection hosts = JCCTraySettings.getInstance().getHosts();

					for (Iterator iterator = hosts.iterator(); iterator.hasNext();) {
						Host host = (Host) iterator.next();
						try {
							DashBoardProject[] dashBoardProjects = DashboardXmlParser.getProjects(host.getHostName())
									.getProjects();
							for (int i = 0; i < dashBoardProjects.length; i++) {
								DashBoardProject project = dashBoardProjects[i];
								project.setHost(host);
								projects.add(project);
							}

						} catch (Exception e) {
							e.printStackTrace();
							// MessageBox messageBox = new
							// MessageBox(table.getShell(), SWT.NONE);
							// messageBox.setText("Cannot connect to " + url);
							// messageBox.setMessage(e.getMessage());
							// messageBox.open();
						}
					}
					tableViewer.setInput(projects);
					updateTrayIcon(projects);
					updateShellIcon(projects);
				}
			}
		});

	}

	protected void updateShellIcon(DashBoardProjects projects) {
		table.getShell().setImage(deduceImageToSet(projects));
	}

}
