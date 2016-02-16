package com.whenling.core.service;

import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.whenling.core.model.User;
import com.whenling.core.repo.UserRepository;
import com.whenling.core.support.security.DatabaseRealm;
import com.whenling.core.support.service.BaseService;

@Service
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DatabaseRealm databaseRealm;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * 修改密码。新用户oldPassword传入null
	 * 
	 * @param entity
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public User changePassword(User entity, String oldPassword, String newPassword) {
		Object salt = new SimpleByteSource(entity.getUsername());

		if (!entity.isNew()) {
			Assert.isTrue(databaseRealm.doCredentialsMatch(oldPassword, new SimpleByteSource(entity.getUsername()),
					entity.getPassword()));
		}

		entity.setPassword(databaseRealm.hashProvidedCredentials(newPassword, salt).toString());
		return entity;
	}

}
