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

import java.beans.IntrospectionException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.sourceforge.jcctray.model.CruiseRegistry;
import net.sourceforge.jcctray.model.CruiseValueSuppressionStrategy;
import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.Host;
import net.sourceforge.jcctray.model.ICruise;
import net.sourceforge.jcctray.model.JCCTraySettings;

import org.apache.commons.betwixt.expression.Context;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.DefaultNameMapper;
import org.apache.commons.betwixt.strategy.ObjectStringConverter;
import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;
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

	public static void save(Object o, String fileName, String rootElement) throws IOException, SAXException,
			IntrospectionException {
		FileWriter outputWriter = new FileWriter(fileName);
		outputWriter.write("<?xml version='1.0' ?>\n");
		BeanWriter beanWriter = new BeanWriter(outputWriter);

		beanWriter.getXMLIntrospector().getConfiguration().setPropertySuppressionStrategy(
				new ProjectPropertySuppressor());
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(true);
		beanWriter.getBindingConfiguration().setValueSuppressionStrategy(new CruiseValueSuppressionStrategy());
		beanWriter.getBindingConfiguration().setObjectStringConverter(new ObjectStringConverter() {
			public String objectToString(Object object, Class type, Context context) {
				if (type == Class.class) {
					Object bean = context.getBean();
					String name = ((Class) bean).getName();
					return name;
				}

				String objectToString = super.objectToString(object, type, context);

				return objectToString;
			}
		});
		beanWriter.getBindingConfiguration().setMapIDs(false);
		beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(true);
		beanWriter.enablePrettyPrint();
		beanWriter.setIndent("\t");
		beanWriter.setInitialIndentLevel(0);

		beanWriter.write(rootElement, o);
		outputWriter.close();
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
