package com.whenling.module.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

public class HashedCredentialsGeneratorAndMatcher extends HashedCredentialsMatcher {

	public HashedCredentialsGeneratorAndMatcher() {
		setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
	}

	public Hash hashProvidedCredentials(Object credentials, Object salt) {
		return super.hashProvidedCredentials(credentials, salt, getHashIterations());
	}
}
