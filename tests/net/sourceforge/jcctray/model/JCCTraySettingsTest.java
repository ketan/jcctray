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
package net.sourceforge.jcctray.model;

import java.util.Collection;

import junit.framework.TestCase;

public class JCCTraySettingsTest extends TestCase {

	private JCCTraySettings	settings;
	private Host			host;
	private Host			host1;
	private Host			host2;

	protected void setUp() throws Exception {
		host = new Host("human Readable Name", "my.host.name");
		host1 = new Host("human Readable Name1", "my.host.name");
		host2 = new Host("human Readable Name2", "my.host.name");
		settings = new JCCTraySettings();
	}

	public void testAddsHosts() throws Exception {
		settings.addHost(host);
		assertEquals(1, settings.hostCount());
		assertEquals(host, settings.findHostByString("human Readable Name"));
	}

	public void testDoesNotAddSameHostTwice() throws Exception {
		settings.addHost(host1);
		settings.addHost(host1);
		assertEquals(1, settings.hostCount());
		assertEquals(host1, settings.findHostByString("human Readable Name1"));
	}

	public void testRemovesHosts() throws Exception {
		settings.addHost(host1);
		settings.addHost(host2);
		settings.removeHost(host1);
		assertEquals(1, settings.hostCount());
		assertEquals(host2, settings.findHostByString("human Readable Name2"));
		assertNull(settings.findHostByString("human Readable Name1"));
	}

	public void testGetsAllHosts() {
		settings.addHost(host1);
		settings.addHost(host2);
		Collection hosts = settings.getHosts();
		assertEquals(2, hosts.size());
		assertTrue(hosts.contains(host1));
		assertTrue(hosts.contains(host2));
	}

	public void testClearsSettings() throws Exception {
		settings.addHost(host1);
		settings.addHost(host2);
		settings.clear();
		assertEquals(0, settings.hostCount());
	}

	public void testPersistsHosts() throws Exception {
		JCCTraySettings settings1 = new JCCTraySettings();
		DashBoardProject dashBoardProject = new DashBoardProject("myProject1", "activity", "asdf", "asdf", "asdf",
				"asdf", "http://", "categpry");
		host1.addProject(dashBoardProject);
		DashBoardProject dashBoardProject2 = new DashBoardProject("myProject2", "activity", "asdf", "asdf", "asdf",
				"asdf", "http://", "categpry");
		host1.addProject(dashBoardProject2);

		DashBoardProject dashBoardProject3 = new DashBoardProject("myProject3", "activity", "asdf", "asdf", "asdf",
				"asdf", "http://", "categpry");
		host2.addProject(dashBoardProject3);
		DashBoardProject dashBoardProject4 = new DashBoardProject("myProject4", "activity", "asdf", "asdf", "asdf",
				"asdf", "http://", "categpry");
		host2.addProject(dashBoardProject4);

		settings1.addHost(host1);
		settings1.addHost(host2);
		settings1.save("jcctray.test.xml");

		JCCTraySettings loadedSettings = new JCCTraySettings();
		loadedSettings.load("jcctray.test.xml");

		assertEquals(2, loadedSettings.hostCount());
		assertTrue(loadedSettings.getHosts().contains(host1));
		assertTrue(loadedSettings.getHosts().contains(host2));

		assertEquals(2, loadedSettings.findHostByString("human Readable Name1").projectCount());

	}
}
