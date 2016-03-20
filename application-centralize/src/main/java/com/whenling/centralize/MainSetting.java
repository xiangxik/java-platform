package com.whenling.centralize;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MainSetting {

	public static final String KEY_NAME = "name";
	public static final String KEY_COMPANY = "company";
	public static final String KEY_VERSION = "version";

	@Resource(name = "mainConfig")
	private Configuration mainConfig;

	public String getName() {
		return mainConfig.getString(KEY_NAME);
	}

	public String getCompany() {
		return mainConfig.getString(KEY_COMPANY);
	}

	public String getVersion() {
		return mainConfig.getString(KEY_VERSION);
	}

	public static MainSetting get() {
		return Application.getBean(MainSetting.class);
	}
}
