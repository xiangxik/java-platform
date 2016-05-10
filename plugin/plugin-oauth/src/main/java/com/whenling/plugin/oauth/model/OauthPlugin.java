package com.whenling.plugin.oauth.model;

import com.whenling.plugin.model.Plugin;
import com.whenling.plugin.model.PluginConfig;

public abstract class OauthPlugin extends Plugin {

	public static final String CLIENT_ID_ATTRIBUTE_NAME = "client_id";

	public static final String CLIENT_SECRET_ATTRIBUTE_NAME = "client_secret";

	public static final String AUTHORIZATION_URL_ATTRIBUTE_NAME = "authorization_url";

	public static final String REDIRECT_URL_ATTRIBUTE_NAME = "redirect_url";

	public static final String ACCESS_TOKEN_URL_ATTRIBUTE_NAME = "access_token_url";

	public static final String API_URL_ATTRIBUTE_NAME = "api_url";

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

	/**
	 * 获取授权URL
	 * 
	 * @return
	 */
	public String getAuthorizationUrl() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(AUTHORIZATION_URL_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取重定向URL
	 * 
	 * @return
	 */
	public String getRedirectUrl() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(REDIRECT_URL_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取访问token的URL
	 * 
	 * @return
	 */
	public String getAccessTokenUrl() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(ACCESS_TOKEN_URL_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 读取接口数据的URL
	 * 
	 * @return
	 */
	public String getApiUrl() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(API_URL_ATTRIBUTE_NAME) : null;
	}

}
