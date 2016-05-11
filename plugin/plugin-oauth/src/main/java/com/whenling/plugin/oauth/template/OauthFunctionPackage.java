package com.whenling.plugin.oauth.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whenling.module.web.view.FunctionPackage;
import com.whenling.plugin.oauth.model.OauthPlugin;
import com.whenling.plugin.oauth.service.OauthService;

@Component("oauthFun")
public class OauthFunctionPackage implements FunctionPackage {

	@Autowired
	private OauthService oauthService;

	public Iterable<OauthPlugin> getOauthPlugins() {
		return oauthService.getOauthPlugins();
	}
}
