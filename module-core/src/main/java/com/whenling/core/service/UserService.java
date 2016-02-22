package com.whenling.core.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.whenling.core.model.Menu;
import com.whenling.core.model.User;
import com.whenling.core.repo.UserRepository;
import com.whenling.core.support.security.DatabaseRealm;
import com.whenling.core.support.security.Principal;
import com.whenling.core.support.service.BaseService;

@Service
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DatabaseRealm databaseRealm;

	@Autowired
	private MenuService menuService;

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
	public User changePassword(User user, String oldPassword, String newPassword) {
		Assert.notNull(user.getUsername());
		Assert.notNull(newPassword);

		Object salt = new SimpleByteSource(user.getUsername());

		if (!user.isNew()) {
			Assert.isTrue(databaseRealm.doCredentialsMatch(oldPassword, new SimpleByteSource(user.getUsername()),
					user.getPassword()));
		}

		user.setPassword(databaseRealm.hashProvidedCredentials(newPassword, salt).toString());
		return user;
	}

	public User getCurrentUser() {
		Object principal = SecurityUtils.getSubject().getPrincipal();
		if (principal != null && principal instanceof Principal) {
			Long currentUserId = ((Principal) principal).getId();
			if (currentUserId != null) {
				return getOne(currentUserId);
			}
		}
		return null;
	}

	public List<Menu> getMenus(User user) {
		return menuService.getRoots();
	}

}
