package com.whenling.plugin.oauth.security;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import com.whenling.plugin.oauth.model.OauthUser;

public class OauthUserToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

	private static final long serialVersionUID = -8356181777546564162L;

	private boolean rememberMe = false;
	private String host;

	private OauthUser oauthUser;

	public OauthUserToken(OauthUser oauthUser, String host, boolean rememberMe) {
		this.rememberMe = rememberMe;
		this.host = host;
		this.oauthUser = oauthUser;
	}

	@Override
	public Object getPrincipal() {
		return oauthUser;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public String getHost() {
		return host;
	}

}
