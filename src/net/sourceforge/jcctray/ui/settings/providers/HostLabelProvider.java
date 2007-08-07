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
/**
 * 
 */
package net.sourceforge.jcctray.ui.settings.providers;

import org.eclipse.jface.viewers.LabelProvider;

import net.sourceforge.jcctray.model.Host;

/**
 * Returns a text representation of a {@link Host}.
 * 
 * @see Host#getHostString()
 * @author Ketan Padegaonkar
 */
public class HostLabelProvider extends LabelProvider {
	public String getText(Object element) {
		return ((Host) element).getHostString();
	}
}
