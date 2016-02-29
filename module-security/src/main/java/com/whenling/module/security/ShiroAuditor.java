package com.whenling.module.security;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.whenling.module.domain.model.User;
import com.whenling.module.domain.repository.UserRepository;

@Component
public class ShiroAuditor implements AuditorAware<User> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getCurrentAuditor() {
		Object principal = SecurityUtils.getSubject().getPrincipal();
		if (principal != null && principal instanceof Principal) {
			Long currentUserId = ((Principal) principal).getId();
			if (currentUserId != null) {
				return userRepository.getOne(currentUserId);
			}
		}
		return null;
	}

}
