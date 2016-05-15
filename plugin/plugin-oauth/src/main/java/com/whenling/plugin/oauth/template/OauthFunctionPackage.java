package com.whenling.plugin.oauth.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.module.web.view.FunctionPackage;
import com.whenling.plugin.oauth.model.OauthPlugin;
import com.whenling.plugin.oauth.service.OauthService;

@Component("oauthFun")
public class OauthFunctionPackage implements FunctionPackage {

	@Autowired
	private OauthService oauthService;;

	public List<OauthPlugin> getPlugins() {
		Iterable<OauthPlugin> it = oauthService.getOauthPlugins();
		if (it == null) {
			return null;
		}
		List<OauthPlugin> oauthPlugins = Lists.newArrayList(it);
		oauthPlugins.sort(null);
		return oauthPlugins;
	}

	public List<OauthPlugin> getEnabledPlugins() {
		List<OauthPlugin> plugins = getPlugins();
		return plugins == null ? null : Lists.newArrayList(Iterables.filter(plugins, plugin -> {
			return plugin.getIsEnabled();
		}));
	}
}
