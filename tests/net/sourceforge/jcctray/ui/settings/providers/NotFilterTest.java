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
package net.sourceforge.jcctray.ui.settings.providers;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import junit.framework.TestCase;

/**
 * @author Ketan Padegaonkar
 */
public class NotFilterTest extends TestCase {

	
	private final class ViewerFilterExtension extends ViewerFilter {
		private boolean	b;

		public ViewerFilterExtension(boolean b) {
			this.b = b;
		}

		public boolean select(Viewer arg0, Object arg1, Object arg2) {
			return b;
		}
	}

	public void testReversesFilter1() throws Exception {
		ViewerFilter viewerFilter = new ViewerFilterExtension(true);
		NotFilter notFilter = new NotFilter(viewerFilter);
		assertFalse(notFilter.select(null, null, null));
	}
	
	public void testReversesFilter2() throws Exception {
		ViewerFilter viewerFilter = new ViewerFilterExtension(false);
		NotFilter notFilter = new NotFilter(viewerFilter);
		assertTrue(notFilter.select(null, null, null));
	}
}
