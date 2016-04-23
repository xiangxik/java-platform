package com.whenling.centralize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.UserRole;
import com.whenling.centralize.repo.UserRoleRepository;
import com.whenling.module.domain.service.BaseService;

/**
 * 用户角色服务
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:30:07
 */
@Service
public class UserRoleService extends BaseService<UserRole, Long> {

	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<UserRole> findByRole(Role role) {
		return userRoleRepository.findByRole(role);
	}

}
