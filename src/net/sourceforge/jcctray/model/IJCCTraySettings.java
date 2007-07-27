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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import net.sourceforge.jcctray.model.JCCTraySettings.NameValuePair;

import org.xml.sax.SAXException;

/**
 * @author Ketan Padegaonkar
 */
public interface IJCCTraySettings {

	public abstract void addHost(Host host);

	public abstract int hostCount();

	public abstract Host findHostByString(String hostString);

	public abstract void clear();

	public abstract void removeHost(Host host);

	public abstract Collection getHosts();

	public abstract void save() throws IOException;

	public abstract void load() throws IOException, SAXException;

	public abstract int getInt(String key);

	public abstract String get(String key);

	public abstract void set(String key, String value);

	public abstract void set(NameValuePair entry);

	public abstract HashMap getSettings();

}