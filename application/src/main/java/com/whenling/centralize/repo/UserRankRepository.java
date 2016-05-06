package com.whenling.centralize.repo;

import com.whenling.centralize.model.UserRank;
import com.whenling.module.domain.repository.BaseRepository;

public interface UserRankRepository extends BaseRepository<UserRank, Long> {

	UserRank findByCode(String code);

	UserRank findByIsDefaultTrue();

}
