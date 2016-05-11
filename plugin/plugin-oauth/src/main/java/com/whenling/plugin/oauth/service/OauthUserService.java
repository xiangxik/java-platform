package com.whenling.plugin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.module.domain.service.BaseService;
import com.whenling.plugin.oauth.model.OauthUser;
import com.whenling.plugin.oauth.repo.OauthUserRepository;

@Service
public class OauthUserService extends BaseService<OauthUser, Long> {

	@Autowired
	private OauthUserRepository oauthUserRepository;

	public OauthUser findByOauthPluginIdAndUserId(String oauthPluginId, String userId) {
		return oauthUserRepository.findByOauthPluginIdAndUserId(oauthPluginId, userId);
	}
}
