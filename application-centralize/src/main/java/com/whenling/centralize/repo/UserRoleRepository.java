package com.whenling.centralize.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.centralize.model.Role;
import com.whenling.centralize.model.UserRole;
import com.whenling.module.domain.model.User;
import com.whenling.module.domain.repository.BaseRepository;

public interface UserRoleRepository extends BaseRepository<UserRole, Long> {

	Page<UserRole> findByUser(User user, Pageable pageable);

	Page<UserRole> findByRole(Role role, Pageable pageable);
}
