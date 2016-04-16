package com.whenling.module.web.view;

import org.beetl.core.Format;

import com.google.common.base.Strings;

public class BooleanFormat implements Format {

	public static final String DEFAULT_PATTERN = "是,否";

	@Override
	public Object format(Object data, String pattern) {
		if (data == null) {
			data = false;
		}
		if (data instanceof Boolean) {
			if (Strings.isNullOrEmpty(pattern)) {
				pattern = DEFAULT_PATTERN;
			}
			String[] trueOrFalse = pattern.split(",");
			return ((Boolean) data) ? trueOrFalse[0] : trueOrFalse[1];
		}
		return null;
	}

}
