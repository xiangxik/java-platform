package com.whenling.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.core.model.Role;
import com.whenling.core.model.User;
import com.whenling.core.model.UserRole;
import com.whenling.core.support.repo.BaseRepository;

public interface UserRoleRepository extends BaseRepository<UserRole, Long> {

	Page<UserRole> findByUser(User user, Pageable pageable);

	Page<UserRole> findByRole(Role role, Pageable pageable);
}
