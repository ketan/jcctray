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

import java.beans.IntrospectionException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class JCCTraySettings implements ISettingsConstants {

	private static final Logger	log	= Logger.getLogger(JCCTraySettings.class);

	private static class ProjectPropertySuppressor extends PropertySuppressionStrategy {
		public boolean suppressProperty(Class classContainingTheProperty, Class propertyType, String propertyName) {
			if (classContainingTheProperty.equals(DashBoardProject.class) && !propertyName.equals("name")
					&& !propertyName.equals("enabled"))
				return true;
			if (propertyType.equals(Class.class) && propertyName.equals("class"))
				return true;
			return false;
		}
	}

	private static JCCTraySettings	instance;

	private HashMap					hosts;

	private HashMap					settings;

	public JCCTraySettings() {
		hosts = new HashMap();
		settings = new HashMap();
		initializeDefaultSettings();
	}

	private void initializeDefaultSettings() {
		set(ISettingsConstants.POLL_INTERVAL, "5");
		set(ISettingsConstants.BROWSER_PATH, "");
	}

	public void addHost(Host host) {
		hosts.put(host.getHostString(), host);
	}

	public int hostCount() {
		return hosts.size();
	}

	public Host findHostByString(String hostString) {
		return (Host) hosts.get(hostString);
	}

	public void clear() {
		hosts.clear();
	}

	public void removeHost(Host host) {
		removeHost(host.getHostString());
	}

	private void removeHost(String hostString) {
		hosts.remove(hostString);
	}

	public Collection getHosts() {
		return new ArrayList(hosts.values());
	}

	public void save() throws IOException, SAXException, IntrospectionException {
		save("jcctray.xml");
	}

	void save(String configFile) throws IOException, SAXException, IntrospectionException {
		FileWriter outputWriter = new FileWriter(configFile);
		outputWriter.write("<?xml version='1.0' ?>\n");
		BeanWriter beanWriter = new BeanWriter(outputWriter);

		beanWriter.getXMLIntrospector().getConfiguration().setPropertySuppressionStrategy(
				new ProjectPropertySuppressor());

		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(true);
		beanWriter.getBindingConfiguration().setValueSuppressionStrategy(new CruiseValueSuppressionStrategy());
		beanWriter.getBindingConfiguration().setMapIDs(false);
		beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);
		beanWriter.enablePrettyPrint();
		beanWriter.setIndent("\t");
		beanWriter.setInitialIndentLevel(0);

		beanWriter.write("cctraysettings", this);
		outputWriter.close();
	}

	public void load() throws IOException, SAXException {
		load("jcctray.xml");
	}

	void load(String fileName) throws IOException, SAXException {
		Digester digester = new Digester();

		FileReader fileReader = new FileReader(fileName);

		digester.addRule("cctraysettings", new ObjectCreateRule(JCCTraySettings.class));

		digester.addRule("cctraysettings/host", new ObjectCreateRule(Host.class));
		digester.addRule("cctraysettings/host", new SetPropertiesRule());
		digester.addRule("cctraysettings/host", new SetNextRule("addHost"));

		digester.addRule("cctraysettings/host/project", new ObjectCreateRule(DashBoardProject.class));
		digester.addRule("cctraysettings/host/project", new SetPropertiesRule());
		digester.addRule("cctraysettings/host/project", new SetNextRule("addProject"));

		JCCTraySettings settings = (JCCTraySettings) digester.parse(fileReader);
		this.hosts = settings.hosts;
		fileReader.close();
	}

	public static JCCTraySettings getInstance() {
		synchronized (JCCTraySettings.class) {
			if (JCCTraySettings.instance == null) {
				JCCTraySettings.instance = new JCCTraySettings();
				try {
					JCCTraySettings.instance.load("jcctray.xml");
				} catch (IOException e) {
					log.error("Exception reading jcctray.xml", e);
					e.printStackTrace();
				} catch (SAXException e) {
					log.error("Exception parsing jcctray.xml", e);
				}
			}
			return JCCTraySettings.instance;
		}
	}

	public int getInt(String key) {
		try {
			return Integer.valueOf(get(key)).intValue();
		} catch (Exception e) {
			log.warn("Error parsing the property '" + key + "'", e);
		}
		return 0;
	}

	public String get(String key) {
		String string = (String) settings.get(key);
		return (string == null) ? "" : string;
	}

	public void set(String key, String value) {
		this.settings.put(key, value);
	}

	public HashMap getSettings() {
		return this.settings;
	}
}
