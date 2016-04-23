package com.whenling.plugin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.module.domain.service.BaseService;
import com.whenling.plugin.model.PluginConfig;
import com.whenling.plugin.repo.PluginConfigRepository;

@Service
public class PluginConfigService extends BaseService<PluginConfig, Long> {

	@Autowired
	private PluginConfigRepository pluginConfigRepository;

	public boolean pluginIdExists(String pluginId) {
		return pluginConfigRepository.countByPluginId(pluginId) > 0;
	}

	public PluginConfig findByPluginId(String pluginId) {
		return pluginConfigRepository.findFirstByPluginId(pluginId);
	}
}
