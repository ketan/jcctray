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

import java.util.TimeZone;

/**
 * This represents the specific implementation of CruiseControl, and helps in
 * forcing a build, and opening a browser on a specific {@link DashBoardProject}.
 * 
 * @author Ketan Padegaonkar
 * @see CruiseControlJava
 * @see CCNet
 * @see CruiseControlRuby
 * @see CruiseControlJava2_7
 * @see HTTPCruise
 */
public interface ICruise {

	public void forceBuild(DashBoardProject project) throws Exception;

	public String getName();

	public String formatDate(String date, TimeZone timeZone);

	public DashBoardProjects getProjects(Host host) throws Exception;
}
