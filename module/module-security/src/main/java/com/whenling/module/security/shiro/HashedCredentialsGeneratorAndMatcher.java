package com.whenling.module.security.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * hash认证匹配
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:08:02
 */
public class HashedCredentialsGeneratorAndMatcher extends HashedCredentialsMatcher {

	public HashedCredentialsGeneratorAndMatcher() {
		setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
	}

	public Hash hashProvidedCredentials(Object credentials, Object salt) {
		return super.hashProvidedCredentials(credentials, salt, getHashIterations());
	}
}
