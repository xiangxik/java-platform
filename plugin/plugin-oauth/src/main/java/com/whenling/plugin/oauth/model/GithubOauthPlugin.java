package com.whenling.plugin.oauth.model;

import org.springframework.stereotype.Component;

import com.whenling.centralize.MainSetting;

@Component("githubOauthPlugin")
public class GithubOauthPlugin extends OauthPlugin {

	@Override
	public String getName() {
		return "github";
	}

	@Override
	public String getVersion() {
		return "3";
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

	@Override
	public String getSettingView() {
		return "app.view.plugin.GithubOauthForm";
	}

	@Override
	public String getSiteUrl() {
		return MainSetting.get().getSiteUrl();
	}

	@Override
	public String getInstallUrl() {
		return "/admin/oauth/github/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/oauth/github/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/oauth/github/setting";
	}

}
