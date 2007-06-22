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
package net.sourceforge.jcctray.exceptions;

public class HTTPErrorException extends Exception {

	private static final long	serialVersionUID	= 4317866513329514582L;

	public HTTPErrorException() {
		super();
	}

	public HTTPErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public HTTPErrorException(String message) {
		super(message);
	}

	public HTTPErrorException(Throwable cause) {
		super(cause);
	}

}
