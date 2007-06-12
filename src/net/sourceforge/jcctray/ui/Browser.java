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
package net.sourceforge.jcctray.ui;

import java.io.IOException;

public class Browser {

	public static void open(String url) {

		try {
			Runtime.getRuntime().exec(new String[] { "firefox", url });
		} catch (IOException e) {
			try {
				Runtime.getRuntime().exec(new String[] { "mozilla", url });
			} catch (IOException e1) {
				try {
					Runtime.getRuntime().exec(new String[] { "konqueror", url });
				} catch (IOException e2) {
					try {
						Runtime.getRuntime().exec(new String[] { "iexplore", url });
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				}

			}

		}

	}
}
