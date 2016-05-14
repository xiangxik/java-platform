package com.whenling.plugin.oauth.model;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whenling.centralize.MainSetting;
import com.whenling.plugin.oauth.service.OauthUserService;

/**
 * http://wiki.connect.qq.com/oauth2-0%e7%ae%80%e4%bb%8b
 * 
 * @author ken
 *
 */
@Component("qqOauthPlugin")
public class QQOauthPlugin extends OauthPlugin {

	@Autowired
	private OauthUserService oauthUserService;

	@Override
	public String getAuthorizationUrl() {
		return "https://graph.qq.com/oauth2.0/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("response_type", "code");
		parameters.put("client_id", getClientId());
		parameters.put("redirect_uri", getRedirectUri());
		parameters.put("state", "test");
		return parameters;
	}

	@Override
	public String getAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("grant_type", "authorization_code");
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("code", code);
		parameterMap.put("redirect_uri", getRedirectUri());
		String responseString = get("https://graph.qq.com/oauth2.0/token", parameterMap);

		List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(responseString, Charset.forName("utf-8"));
		Map<String, Object> result = new HashMap<>();
		for (NameValuePair nameValuePair : nameValuePairs) {
			result.put(nameValuePair.getName(), nameValuePair.getValue());
		}

		return getParameter(nameValuePairs, "access_token");
	}

	@Override
	public OauthUser getOauthUser(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("access_token", accessToken);
		String responseString = get("https://graph.qq.com/oauth2.0/me", parameterMap);
		responseString = StringUtils.trim(responseString);
		responseString = StringUtils.removeStartIgnoreCase(responseString, "callback(");
		responseString = StringUtils.removeEndIgnoreCase(responseString, ");");
		JSONObject jsonObject = JSON.parseObject(responseString);

		String openid = jsonObject.getString("openid");
		OauthUser oauthUser = oauthUserService.findByOauthPluginIdAndUserId(getId(), openid);
		if (oauthUser == null) {
			Map<String, Object> apiMap = new HashMap<>();
			apiMap.put("access_token", accessToken);
			apiMap.put("oauth_consumer_key", jsonObject.getString("client_id"));
			apiMap.put("openid", openid);
			String apiString = get("https://graph.qq.com/user/get_user_info", apiMap);
			JSONObject userObject = JSON.parseObject(apiString);

			oauthUser = oauthUserService.newEntity();
			oauthUser.setOauthPluginId(getId());
			oauthUser.setUserId(openid);
			oauthUser.setUsername(openid);
			oauthUser.setName(userObject.getString("nickname"));
			oauthUser.setAvatarUrl(userObject.getString("figureurl_qq_2"));
		}

		return oauthUser;
	}

	@Override
	public String getName() {
		return "QQ";
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
