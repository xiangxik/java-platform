package com.whenling.plugin.oauth;

import org.springframework.stereotype.Component;

import com.whenling.centralize.Application;
import com.whenling.centralize.Extension;

@Component
public class OauthPluginExtension extends Extension {

	@Override
	public void init(Application app, boolean isNew, boolean isUpdate, Integer historyVersion) {
		if (isNew) {
			app.addMenu("认证插件", "oauthplugin", "Brick", "app.view.plugin.OauthPluginList", null, null);
		}
	}

	@Override
	public Integer getVersion() {
		return 1;
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

}
