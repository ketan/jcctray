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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import net.sourceforge.jcctray.utils.FileUtil;
import net.sourceforge.jcctray.utils.ObjectPersister;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * A registry to maintain a list of all CI servers that JCCTray is aware of.
 * 
 * @author Ketan Padegaonkar
 */
public class CruiseRegistry {

	private static final Logger		log	= Logger.getLogger(CruiseRegistry.class);
	private static CruiseRegistry	instance;
	private HashSet					registry;

	public CruiseRegistry() {
		this.registry = new HashSet();
	}

	public static CruiseRegistry getInstance() {
		synchronized (CruiseRegistry.class) {
			if (CruiseRegistry.instance == null) {
				CruiseRegistry.instance = new CruiseRegistry();
				try {
					CruiseRegistry.load("cruiseregistry.xml");
				} catch (FileNotFoundException e) {
					// we don't care if the settings do not exist
				} catch (SAXException e) {
					log.error("Exception parsing jcctray.xml", e);
				} catch (IOException e) {
					log.error("Exception reading jcctray.xml", e);
				}
				instance.init();
			}
			return CruiseRegistry.instance;
		}
	}

	protected void init() {
		addCruiseImpl(CCNet.class);
		addCruiseImpl(Hudson.class);
		addCruiseImpl(CruiseControlJava.class);
		addCruiseImpl(CruiseControlRuby.class);
		addCruiseImpl(CruiseControlJava2_7.class);
		addCruiseImpl(Go.class);
	}

	public void addCruiseImpl(Class clazz) {
		if (ICruise.class.isAssignableFrom(clazz)) {
			registry.add(clazz);
		}
	}

	public void removeCruiseImpl(Class clazz) {
		registry.remove(clazz);
	}

	public Collection getCruiseImpls() {
		return new HashSet(registry);
	}

	public void save() throws IOException, SAXException, IntrospectionException {
		save (FileUtil.findConfigFile("cruiseregistry.xml"));
	}
	
	void save(String fileName) throws IOException, SAXException, IntrospectionException {
		ObjectPersister.saveCruiseRegistry(this, fileName);
	}

	public static CruiseRegistry load(String fileName) throws IOException, SAXException {
		return (CruiseRegistry) ObjectPersister.loadCruiseRegistry(fileName);
	}
}
