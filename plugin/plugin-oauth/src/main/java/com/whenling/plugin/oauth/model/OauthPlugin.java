package com.whenling.plugin.oauth.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.whenling.plugin.model.Plugin;
import com.whenling.plugin.model.PluginConfig;
import com.whenling.plugin.oauth.service.OauthUserService;

public abstract class OauthPlugin extends Plugin {

	public static final String CLIENT_ID_ATTRIBUTE_NAME = "client_id";

	public static final String CLIENT_SECRET_ATTRIBUTE_NAME = "client_secret";

	@Autowired
	protected OauthUserService oauthUserService;

	/**
	 * 获取客户端ID
	 * 
	 * @return
	 */
	public String getClientId() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(CLIENT_ID_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取客户端密钥
	 * 
	 * @return
	 */
	public String getClientSecret() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(CLIENT_SECRET_ATTRIBUTE_NAME) : null;
	}

	public abstract String getAuthorizationUrl();

	public abstract Map<String, Object> getAuthorizationParameterMap();

	public abstract String getAccessToken(String code);

	public abstract OauthUser accessApi(String accessToken);

}
