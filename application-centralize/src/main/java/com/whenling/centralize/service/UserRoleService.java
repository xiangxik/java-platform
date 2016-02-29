package com.whenling.centralize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.UserRole;
import com.whenling.centralize.repo.UserRoleRepository;
import com.whenling.module.domain.model.User;
import com.whenling.module.domain.service.BaseService;

@Service
public class UserRoleService extends BaseService<UserRole, Long> {

	@Autowired
	private UserRoleRepository userRoleRepository;

	public Page<User> findByRole(Role role, Pageable pageable) {
		return userRoleRepository.findByRole(role, pageable).map(UserRoleService::toUser);
	}

	public Page<Role> findByUser(User user, Pageable pageable) {
		return userRoleRepository.findByUser(user, pageable).map(UserRoleService::toRole);
	}

	public static User toUser(UserRole userRole) {
		return userRole.getUser();
	}

	public static Role toRole(UserRole userRole) {
		return userRole.getRole();
	}
}
