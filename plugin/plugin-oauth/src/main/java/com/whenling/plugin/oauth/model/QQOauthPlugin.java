package com.whenling.plugin.oauth.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.whenling.centralize.MainSetting;

/**
 * http://wiki.connect.qq.com/oauth2-0%e7%ae%80%e4%bb%8b
 * 
 * @author ken
 *
 */
@Component("qqOauthPlugin")
public class QQOauthPlugin extends OauthPlugin {

	@Override
	public String getAuthorizationUrl() {
		return "https://graph.qq.com/oauth2.0/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("response_type", "code");
		parameters.put("client_id", getClientId());
		parameters.put("redirect_uri", "http://localhost:8080/oauth/api/" + getId());
		parameters.put("state", "test");
		return parameters;
	}

	@Override
	public String getAccessToken(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OauthUser accessApi(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "qq";
	}

	@Override
	public String getVersion() {
		return "1";
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

	@Override
	public String getSettingView() {
		return "app.view.plugin.QQOauthForm";
	}

	@Override
	public String getSiteUrl() {
		return MainSetting.get().getSiteUrl();
	}

	@Override
	public String getInstallUrl() {
		return "/admin/oauth/qq/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/oauth/qq/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/oauth/qq/setting";
	}

}
