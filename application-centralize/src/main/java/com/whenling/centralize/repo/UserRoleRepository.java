package com.whenling.centralize.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.UserRole;
import com.whenling.module.domain.model.User;
import com.whenling.module.domain.repository.BaseRepository;

/**
 * 用户角色仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:27:10
 */
public interface UserRoleRepository extends BaseRepository<UserRole, Long> {

	Page<UserRole> findByUser(User user, Pageable pageable);

	Page<UserRole> findByRole(Role role, Pageable pageable);
}
