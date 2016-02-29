package com.whenling.module.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.whenling.module.domain.model.User;
import com.whenling.module.domain.repository.UserRepository;

public class DatabaseRealm extends AuthorizingRealm {

	@Autowired
	private UserRepository userRepository;

	public DatabaseRealm() {
		setCachingEnabled(true);
		setAuthenticationCachingEnabled(true);
		setAuthenticationCacheName("authenticationCache");
		setAuthorizationCachingEnabled(true);
		setAuthorizationCacheName("authorizationCache");

		setCredentialsMatcher(new HashedCredentialsGeneratorAndMatcher());
	}

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
				return null;
			}

			return new SimpleAuthenticationInfo(new Principal(user.getId(), username), user.getPassword(),
					new SimpleByteSource(user.getUsername()), getName());
		}
		return null;
	}

	public boolean doCredentialsMatch(Object tokenCredentials, ByteSource salt, Object accountCredentials) {
		Object principal = new Principal();
		return getCredentialsMatcher().doCredentialsMatch(new SimpleAuthenticationToken(principal, tokenCredentials),
				new SimpleAuthenticationInfo(principal, accountCredentials, salt, getName()));
	}

	public Hash hashProvidedCredentials(Object credentials, Object salt) {
		return ((HashedCredentialsGeneratorAndMatcher) getCredentialsMatcher()).hashProvidedCredentials(credentials,
				salt);
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

}
