package com.whenling.module.web.view;

import org.beetl.ext.format.DateFormat;
import org.joda.time.DateTime;

public class DateTimeFormat extends DateFormat {

	@Override
	public Object format(Object data, String pattern) {
		if (data == null)
			return null;
		if (data instanceof DateTime) {
			return super.format(((DateTime) data).toDate(), pattern);
		}
		return super.format(data, pattern);
	}

}
