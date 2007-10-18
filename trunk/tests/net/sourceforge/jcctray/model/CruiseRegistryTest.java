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

import java.io.File;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar
 */
public class CruiseRegistryTest extends TestCase {

	private CruiseRegistry	registry;

	protected void setUp() throws Exception {
		registry = new CruiseRegistry();
	}

	protected void tearDown() throws Exception {
		new File("cruiseRegistry.test.xml").delete();
	}

	public void testRegistersCCImplementations() throws Exception {
		registry.addCruiseImpl(CCNet.class);
		assertEquals(1, registry.getCruiseImpls().size());
		assertTrue(registry.getCruiseImpls().contains(CCNet.class));
	}

	public void testUnRegistersCCImplementations() throws Exception {
		registry.addCruiseImpl(CCNet.class);
		registry.removeCruiseImpl(CCNet.class);
		assertEquals(0, registry.getCruiseImpls().size());
		assertFalse(registry.getCruiseImpls().contains(CCNet.class));
	}

	public void testSavesAndLoadsRegistry() throws Exception {
		registry.addCruiseImpl(CCNet.class);
		registry.addCruiseImpl(CruiseControlJava.class);

		registry.save("cruiseRegistry.test.xml");
		CruiseRegistry load = CruiseRegistry.load("cruiseRegistry.test.xml");
		assertNotNull(load);
	}
}
