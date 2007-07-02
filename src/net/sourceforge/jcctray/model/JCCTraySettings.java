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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.sourceforge.jcctray.utils.ObjectPersister;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class JCCTraySettings implements ISettingsConstants {

	public static final class NameValuePair {
		
		private Object key;
		private Object value;
		
        public Object getKey() {
            return this.key;
        }

        public Object getValue() {
            return value;
        }
    
        public void setValue(Object value) {
            this.value = value;
        }
        
		public void setKey(Object key) {
			this.key = key;
		}
    
        public String toString() {
            return getKey() + "=" + getValue();
        }

	}

	private static final Logger		log	= Logger.getLogger(JCCTraySettings.class);

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
		settings.clear();
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

	public void save() throws IOException {
		save("jcctray.xml");
	}

	void save(String configFile) throws IOException {
		ObjectPersister.saveSettings(this, configFile);
	}

	public void load() throws IOException, SAXException {
		load("jcctray.xml");
	}

	void load(String fileName) throws IOException, SAXException {
		JCCTraySettings traySettings = ObjectPersister.loadJCCTraySettings(fileName);
		this.hosts = traySettings.hosts;
		this.settings = traySettings.settings;
	}

	public static JCCTraySettings getInstance() {
		synchronized (JCCTraySettings.class) {
			if (JCCTraySettings.instance == null) {
				JCCTraySettings.instance = new JCCTraySettings();
				try {
					JCCTraySettings.instance.load("jcctray.xml");
				} catch (FileNotFoundException e) {
					// we don't care if the settings do not exist
				} catch (SAXException e) {
					log.error("Exception parsing jcctray.xml", e);
				} catch (IOException e) {
					log.error("Exception reading jcctray.xml", e);
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
	
	public void set(NameValuePair entry) {
		this.settings.put(entry.getKey(), entry.getValue());
	}
	
	public HashMap getSettings() {
		return this.settings;
	}
}
