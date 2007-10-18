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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * An implementation of {@link ICruise} that connects to CruiseControl.rb (<a
 * href="http://cruisecontrolrb.thoughtworks.com">http://cruisecontrolrb.thoughtworks.com</a>)
 * 
 * @author Ketan Padegaonkar
 */
public class CruiseControlRuby extends HTTPCruise implements ICruise {

	private static final Locale				LOCALE_US	= Locale.US;
	private static final SimpleDateFormat	DATE_PARSER	= new SimpleDateFormat("yyyy-MM-dd'T'HHmmssZ", LOCALE_US);

	protected String forceBuildURL(DashBoardProject project) {
		return project.getHost().getHostName().replaceAll("/*$", "") + "/projects/build/" + project.getName();
	}

	protected String getSuccessMessage(DashBoardProject project) {
		return ""; // anything passes
	}

	protected String getXmlReportURL(Host host) {
		return host.getHostName().replaceAll("/*$", "") + "/XmlStatusReport.aspx";
	}

	protected HttpMethod httpMethod(DashBoardProject project) {
		HttpMethod method = new PostMethod(forceBuildURL(project));
		configureMethod(method, project);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		return method;
	}

	public String formatDate(String date, TimeZone timeZone) {
		try {
			String theDate = date.replaceAll("\\.\\d+", "").replaceAll(":", "");
			return getDateFormatter(timeZone).format(DATE_PARSER.parse(theDate));
		} catch (Exception e) {
			getLog().error("Could not parse date: " + date);
		}
		return date;
	}

	private SimpleDateFormat getDateFormatter(TimeZone timeZone) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm:ss a, dd MMM", LOCALE_US);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(timeZone);
		dateFormatter.setCalendar(calendar);
		return dateFormatter;
	}

	public String getName() {
		return "CruiseControl.rb";
	}

}
