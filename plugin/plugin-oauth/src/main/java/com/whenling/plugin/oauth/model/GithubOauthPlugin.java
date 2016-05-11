package com.whenling.plugin.oauth.model;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
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
		return parameterMap;
	}

	@Override
	public String getAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("code", code);
		String accessTokenResponse = post("https://github.com/login/oauth/access_token", parameterMap);
		List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(accessTokenResponse, Charset.forName("utf-8"));
		String accessToken = getParameter(nameValuePairs, "access_token");
		return accessToken;
	}

	@Override
	public OauthUser accessApi(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> apiParameterMap = new HashMap<>();
		apiParameterMap.put("access_token", accessToken);
		String apiResponse = get("https://api.github.com/user", apiParameterMap);
		GithubUser githubUser = null;
		if (StringUtils.startsWith(apiResponse, "{")) {
			githubUser = JSON.parseObject(apiResponse, GithubUser.class);
		} else {
			List<GithubUser> githubUsers = JSON.parseArray(apiResponse, GithubUser.class);
			if (githubUsers != null && githubUsers.size() > 0) {
				githubUser = githubUsers.get(0);
			}
		}

		if (githubUser == null) {
			throw new RuntimeException();
		}
		String userId = String.valueOf(githubUser.getId());
		OauthUser oauthUser = oauthUserService.findByOauthPluginIdAndUserId(getId(), userId);
		if (oauthUser == null) {
			oauthUser = oauthUserService.newEntity();
			oauthUser.setOauthPluginId(getId());
			oauthUser.setUserId(userId);
			oauthUser.setUsername(githubUser.getLogin());
			oauthUser.setName(githubUser.getName());
			oauthUser.setAvatarUrl(githubUser.getAvatar_url());
		}
		return oauthUser;

	}

	public static class GithubUser {
		private Long id;
		private String login;
		private String avatar_url;
		private String name;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getAvatar_url() {
			return avatar_url;
		}

		public void setAvatar_url(String avatar_url) {
			this.avatar_url = avatar_url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
