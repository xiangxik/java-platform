package com.whenling.plugin.oauth.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.whenling.centralize.MainSetting;

/**
 * https://developer.github.com/v3/oauth/
 * 
 * @author ken
 *
 */
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

	@Override
	public String getAuthorizationUrl() {
		return "https://github.com/login/oauth/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("redirect_uri", getSiteUrl() + "/oauth/api/" + getId());
		return parameterMap;
	}

	@Override
	public String exchangeCodeForAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("code", code);
		String accessTokenResponse = post("https://github.com/login/oauth/access_token", parameterMap);
		System.out.println(accessTokenResponse);

		return accessTokenResponse;
	}

	@Override
	public void accessApi(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("access_token", accessToken);
		String apiResponse = get("https://api.github.com/user", parameterMap);
		System.out.println(apiResponse);
	}

}
