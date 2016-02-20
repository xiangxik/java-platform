package com.whenling.core.support.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.whenling.core.model.User;
import com.whenling.core.service.UserService;

@Component
public class ShiroAuditor implements AuditorAware<User> {

	@Autowired
	private UserService userService;

	@Override
	public User getCurrentAuditor() {
		return userService.getCurrentUser();
	}

}
