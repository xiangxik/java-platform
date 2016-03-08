package com.whenling.centralize.support.web.extjs.template;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component("thumb")
public class ThumbnailFunction implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		if (paras.length == 1) {
			String source = (String) paras[0];
			if (!Strings.isNullOrEmpty(source)) {
				String path = StringUtils.substringBeforeLast(source, ".");
				String extension = StringUtils.substringAfterLast(source, ".");
				return path + "-thumb." + extension;
			}
		}
		return null;
	}

}
