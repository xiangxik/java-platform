package com.whenling.centralize.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.whenling.centralize.model.Menu;
import com.whenling.centralize.model.User;
import com.whenling.centralize.repo.UserRepository;
import com.whenling.centralize.support.security.DatabaseRealm;
import com.whenling.module.domain.model.Tree;
import com.whenling.module.domain.service.BaseService;
import com.whenling.module.security.shiro.Principal;

/**
 * 用户服务
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:30:19
 */
@Service
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DatabaseRealm databaseRealm;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserRankService userRankService;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findByMobile(String mobile) {
		return userRepository.findByMobile(mobile);
	}

	public User findSuperAdmin() {
		return userRepository.findBySuperAdminTrue();
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

	public Long getCurrentUserId() {
		Object principal = SecurityUtils.getSubject().getPrincipal();
		if (principal != null && principal instanceof Principal) {
			Long currentUserId = ((Principal) principal).getId();
			return currentUserId;
		}
		return null;
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
			Assert.isTrue(databaseRealm.doCredentialsMatch(oldPassword, new SimpleByteSource(user.getUsername()), user.getPassword()));
		}

		user.setPassword(databaseRealm.hashProvidedCredentials(newPassword, salt).toString());
		return user;
	}

	public Tree<Menu> getMenus(User user) {
		if (user.isSuperAdmin()) {
			return menuService.findTree(null);
		}
		Set<Menu> menus = new HashSet<>();
		user.getUserRoles().forEach(userRole -> {
			menus.addAll(userRole.getRole().getMenus());
		});
		return menuService.toTree(null, Lists.newArrayList(menus));
	}

	@Override
	public User save(User entity) {
		if (entity.isNew() && entity.getRank() == null) {
			entity.setRank(userRankService.getDefault());
		}
		return super.save(entity);
	}

}
