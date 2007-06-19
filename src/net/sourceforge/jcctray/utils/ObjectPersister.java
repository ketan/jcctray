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
package net.sourceforge.jcctray.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import net.sourceforge.jcctray.model.CruiseRegistry;
import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.Host;
import net.sourceforge.jcctray.model.JCCTraySettings;

import org.apache.commons.digester.CallMethodRule;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreateRule;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.xml.sax.SAXException;

/**
 * @author Ketan Padegaonkar
 */
public class ObjectPersister {

	public static void saveSettings(JCCTraySettings settings, String fileName) throws IOException {
		FileWriter outputWriter = new FileWriter(fileName);
		saveSettings(settings, outputWriter);
	}

	public static void saveSettings(JCCTraySettings settings, Writer writer) throws IOException {
		try {
			writer.write("<?xml version='1.0' ?>\n");
			writer.write("<cctraysettings>\n");
			saveHosts(writer, settings.getHosts());
			writer.write("</cctraysettings>\n");
		} catch (IOException e) {
			throw e;
		} finally {
			writer.close();
		}
	}

	public static void saveCruiseRegistry(CruiseRegistry registry, String fileName) throws IOException {
		saveCruiseRegistry(registry, new FileWriter(fileName));
	}

	public static void saveCruiseRegistry(CruiseRegistry registry, FileWriter writer) throws IOException {
		try {
			writer.write("<?xml version='1.0' ?>\n");
			writer.write("<cruiseregistry>\n");
			writer.write("	<cruiseImpls>\n");
			saveCruiseImpls(writer, registry.getCruiseImpls());
			writer.write("	</cruiseImpls>\n");
			writer.write("</cruiseregistry>\n");
		} catch (IOException e) {
			throw e;
		} finally {
			writer.close();
		}
	}

	private static void saveCruiseImpls(FileWriter writer, Collection cruiseImpls) throws IOException {
		for (Iterator iterator = cruiseImpls.iterator(); iterator.hasNext();) {
			writer.write("		<cruiseImpl>" + ((Class) iterator.next()).getName() + "</cruiseImpl>\n");
		}
	}

	private static void saveHosts(Writer writer, Collection hosts) throws IOException {
		writer.write("	<hosts>\n");
		for (Iterator iterator = hosts.iterator(); iterator.hasNext();) {
			Host host = (Host) iterator.next();
			writer.write("		<host");
			writer.write(" cruiseClass = \"" + host.getCruiseClass() + "\"");
			writer.write(" hostName = \"" + host.getHostName() + "\"");
			writer.write(" hostString = \"" + host.getHostString() + "\"");
			writer.write(">\n");
			saveProjects(writer, host.getProjects());
			writer.write("		</host>\n");
		}
		writer.write("	</hosts>\n");
	}

	private static void saveProjects(Writer writer, Collection projects) throws IOException {
		writer.write("			<projects>\n");
		for (Iterator iterator2 = projects.iterator(); iterator2.hasNext();) {
			DashBoardProject project = (DashBoardProject) iterator2.next();
			writer.write("				<project");
			writer.write(" enabled = \"" + project.isEnabled() + "\"");
			writer.write(" name = \"" + project.getName() + "\"");
			writer.write("/>\n");
		}
		writer.write("			</projects>\n");
	}

	public static JCCTraySettings loadJCCTraySettings(String fileName) throws IOException, SAXException {
		Digester digester = new Digester();

		FileReader fileReader = new FileReader(fileName);

		digester.addRule("cctraysettings", new ObjectCreateRule(JCCTraySettings.class));

		digester.addRule("cctraysettings/hosts/host", new ObjectCreateRule(Host.class));
		digester.addRule("cctraysettings/hosts/host", new SetPropertiesRule());
		digester.addRule("cctraysettings/hosts/host", new SetNextRule("addHost"));

		digester.addRule("cctraysettings/hosts/host/projects/project", new ObjectCreateRule(DashBoardProject.class));
		digester.addRule("cctraysettings/hosts/host/projects/project", new SetPropertiesRule());
		digester.addRule("cctraysettings/hosts/host/projects/project", new SetNextRule("addProject"));

		JCCTraySettings result = (JCCTraySettings) digester.parse(fileReader);
		fileReader.close();
		return result;
	}

	public static CruiseRegistry loadCruiseRegistry(String fileName) throws IOException, SAXException {
		Digester digester = new Digester();

		FileReader fileReader = new FileReader(fileName);
		digester.addRule("cruiseregistry", new ObjectCreateRule(CruiseRegistry.class));
		digester.addRule("cruiseregistry/cruiseImpls/cruiseImpl", new CallMethodRule("addCruiseImpl", 0,
				new Class[] { Class.class }));
		CruiseRegistry result = (CruiseRegistry) digester.parse(fileReader);
		fileReader.close();
		return result;
	}

}