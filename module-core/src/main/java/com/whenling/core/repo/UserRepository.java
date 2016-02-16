package com.whenling.core.repo;

import com.whenling.core.model.User;
import com.whenling.core.support.repo.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

	User findByUsername(String username);

}
