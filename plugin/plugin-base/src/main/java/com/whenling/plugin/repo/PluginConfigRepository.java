package com.whenling.plugin.repo;

import com.whenling.module.domain.repository.BaseRepository;
import com.whenling.plugin.model.PluginConfig;

public interface PluginConfigRepository extends BaseRepository<PluginConfig, Long> {

	Long countByPluginId(String pluginId);

	PluginConfig findFirstByPluginId(String pluginId);

}
