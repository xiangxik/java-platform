package com.whenling.centralize;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.springframework.stereotype.Component;

import com.whenling.module.base.SpringContext;

@Component
public class MainSetting {

	public static final String KEY_NAME = "name";
	public static final String KEY_COMPANY = "company";
	public static final String KEY_VERSION = "version";
	public static final String KEY_SITEURL = "siteUrl";
	public static final String KEY_SSLURL = "sslUrl";

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

	public String getSiteUrl() {
		return mainConfig.getString(KEY_SITEURL);
	}

	public String getSslUrl() {
		return mainConfig.getString(KEY_SSLURL);
	}

	public static MainSetting get() {
		return SpringContext.getBean(MainSetting.class);
	}
}
