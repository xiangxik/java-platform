package com.whenling.centralize.support.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.whenling.centralize.model.User;
import com.whenling.centralize.service.UserService;

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
	private UserService userService;

	@Override
	public User getCurrentAuditor() {
		return userService.getCurrentUser();
	}

}
