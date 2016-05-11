package com.whenling.plugin.oauth.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whenling.centralize.model.User;
import com.whenling.module.security.shiro.Principal;
import com.whenling.plugin.oauth.model.OauthUser;

@Component
public class OauthUserRealm extends AuthorizingRealm implements InitializingBean {

	@Autowired
	private CacheManager cacheManager;

	public static final String CACHE_AUTHENTICATION = "authenticationCache";
	public static final String CACHE_AUTHORIZATION = "authorizationCache";

	@Override
	public void afterPropertiesSet() throws Exception {
		setCachingEnabled(true);
		setCacheManager(cacheManager);
		setAuthenticationCachingEnabled(true);
		setAuthenticationCacheName(CACHE_AUTHENTICATION);
		setAuthorizationCachingEnabled(true);
		setAuthorizationCacheName(CACHE_AUTHORIZATION);
		setAuthenticationTokenClass(OauthUserToken.class);
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		OauthUser oauthUser = (OauthUser) token.getPrincipal();
		if (oauthUser == null || oauthUser.getOwner() == null) {
			return null;
		}

		User user = oauthUser.getOwner();

		return new SimpleAuthenticationInfo(new Principal(user.getId(), user.getUsername()), null, getName());
	}

}
