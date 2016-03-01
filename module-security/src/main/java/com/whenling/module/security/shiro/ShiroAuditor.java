package com.whenling.module.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.whenling.module.domain.model.User;
import com.whenling.module.domain.repository.UserRepository;

/**
 * 审计器。用于求得创建人和修改人
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:08:25
 */
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
