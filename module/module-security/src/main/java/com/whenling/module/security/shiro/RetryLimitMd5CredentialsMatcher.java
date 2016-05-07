package com.whenling.module.security.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * hash认证匹配
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:08:02
 */
@Component
public class RetryLimitMd5CredentialsMatcher extends HashedCredentialsMatcher {

	@Value("${password.maxRetryCount?:10}")
	private Integer maxRetryCount;

	private Map<String, AtomicInteger> passwordRetryCache = new HashMap<>();

	public RetryLimitMd5CredentialsMatcher() {
		setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if (token instanceof UsernamePasswordToken) {
			String username = ((UsernamePasswordToken) token).getUsername();
			AtomicInteger retryCount = passwordRetryCache.get(username);
			if (retryCount == null) {
				retryCount = new AtomicInteger(0);
				passwordRetryCache.put(username, retryCount);
			}
			if (retryCount.incrementAndGet() > maxRetryCount) {
				throw new ExcessiveAttemptsException();
			}

			boolean matched = super.doCredentialsMatch(token, info);
			if (matched) {
				passwordRetryCache.remove(username);
			}
			return matched;
		}

		return super.doCredentialsMatch(token, info);
	}

	public Hash hashProvidedCredentials(Object credentials, Object salt) {
		return super.hashProvidedCredentials(credentials, salt, getHashIterations());
	}
}
