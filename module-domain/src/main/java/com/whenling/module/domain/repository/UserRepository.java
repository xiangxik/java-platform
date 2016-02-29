package com.whenling.module.domain.repository;

import com.whenling.module.domain.model.User;

public interface UserRepository extends BaseRepository<User, Long> {

	User findByUsername(String username);

}
