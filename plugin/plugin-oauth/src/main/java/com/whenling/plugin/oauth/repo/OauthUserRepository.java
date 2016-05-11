package com.whenling.plugin.oauth.repo;

import com.whenling.module.domain.repository.BaseRepository;
import com.whenling.plugin.oauth.model.OauthUser;

public interface OauthUserRepository extends BaseRepository<OauthUser, Long> {

	OauthUser findByOauthPluginIdAndUserId(String oauthPluginId, String userId);

}
