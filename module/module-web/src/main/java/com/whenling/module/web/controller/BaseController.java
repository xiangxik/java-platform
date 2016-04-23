package com.whenling.module.web.controller;

import com.whenling.module.web.view.ViewConfiguration;

public abstract class BaseController {

	protected String template(String path) {
		return ViewConfiguration.VIEW_PREFIX_TEMPLATE + path;
	}

	protected String classpath(String path) {
		return ViewConfiguration.VIEW_PREFIX_CLASSPATH + path;
	}

}
