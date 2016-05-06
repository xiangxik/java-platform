package com.whenling.centralize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.centralize.model.UserRank;
import com.whenling.centralize.repo.UserRankRepository;
import com.whenling.module.domain.service.BaseService;

@Service
public class UserRankService extends BaseService<UserRank, Long> {

	@Autowired
	private UserRankRepository userRankRepository;

	public UserRank getDefault() {
		return userRankRepository.findByIsDefaultTrue();
	}

	public UserRank setDefault(UserRank userRank) {
		for (UserRank rank : findAll()) {
			rank.setIsDefault(false);
			save(rank);
		}
		userRank.setIsDefault(true);
		return save(userRank);
	}

	public UserRank findByCode(String code) {
		return userRankRepository.findByCode(code);
	}

}
