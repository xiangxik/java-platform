package com.whenling.centralize.support.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.whenling.centralize.model.User;
import com.whenling.centralize.repo.UserRepository;
import com.whenling.module.security.shiro.Principal;
import com.whenling.module.security.shiro.RetryLimitMd5CredentialsMatcher;

/**
 * 数据库域
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:06:54
 */
@Component
public class DatabaseRealm extends AuthorizingRealm implements InitializingBean {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CacheManager cacheManager;

	@Value("${password.maxRetryCount?:10}")
	private Integer maxRetryCount;

	public static final String CACHE_AUTHENTICATION = "authenticationCache";
	public static final String CACHE_AUTHORIZATION = "authorizationCache";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (token instanceof UsernamePasswordToken) {
			String username = ((UsernamePasswordToken) token).getUsername();
			char[] password = ((UsernamePasswordToken) token).getPassword();

			if (Strings.isNullOrEmpty(username) || password == null) {
				return null;
			}

			User user = userRepository.findByUsername(username);
			if (user == null) {
				throw new UnknownAccountException();
			}

			return new SimpleAuthenticationInfo(new Principal(user.getId(), username), user.getPassword(), new SimpleByteSource(user.getUsername()),
					getName());
		}
		return null;
	}

	public boolean doCredentialsMatch(Object tokenCredentials, ByteSource salt, Object accountCredentials) {
		Object principal = new Principal();
		return getCredentialsMatcher().doCredentialsMatch(new SimpleAuthenticationToken(principal, tokenCredentials),
				new SimpleAuthenticationInfo(principal, accountCredentials, salt, getName()));
	}

	public Hash hashProvidedCredentials(Object credentials, Object salt) {
		return ((RetryLimitMd5CredentialsMatcher) getCredentialsMatcher()).hashProvidedCredentials(credentials, salt);
	}

	class SimpleAuthenticationToken implements AuthenticationToken {

		private static final long serialVersionUID = -6002059279423529574L;

		private Object principal;
		private Object credentials;

		public SimpleAuthenticationToken(Object principal, Object credentials) {
			this.principal = principal;
			this.credentials = credentials;
		}

		@Override
		public Object getPrincipal() {
			return principal;
		}

		@Override
		public Object getCredentials() {
			return credentials;
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setCachingEnabled(true);
		setCacheManager(cacheManager);
		setAuthenticationCachingEnabled(true);
		setAuthenticationCacheName(CACHE_AUTHENTICATION);
		setAuthorizationCachingEnabled(true);
		setAuthorizationCacheName(CACHE_AUTHORIZATION);
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		setCredentialsMatcher(new RetryLimitMd5CredentialsMatcher(cacheManager, maxRetryCount));
	}

}
