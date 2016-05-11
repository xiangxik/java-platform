package com.whenling.plugin.oauth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whenling.module.web.controller.BaseController;
import com.whenling.plugin.oauth.model.OauthPlugin;
import com.whenling.plugin.oauth.service.OauthService;

@Controller
@RequestMapping("/oauth")
public class OauthController extends BaseController {

	@Autowired
	private OauthService oauthService;

	@RequestMapping(value = "/authorization/{oauthPluginId}", method = RequestMethod.GET)
	public String redirectUsersToRequestAccess(@PathVariable("oauthPluginId") String oauthPluginId, RedirectAttributes redirectAttributes) {
		OauthPlugin oauthPlugin = oauthService.getOauthPlugin(oauthPluginId);
		redirectAttributes.addAllAttributes(oauthPlugin.getAuthorizationParameterMap());
		return "redirect:" + oauthPlugin.getAuthorizationUrl();
	}

	@RequestMapping(value = "/api/{oauthPluginId}")
	public void accessApi(@PathVariable("oauthPluginId") String oauthPluginId, String code) {
		OauthPlugin oauthPlugin = oauthService.getOauthPlugin(oauthPluginId);
		String accessToken = oauthPlugin.exchangeCodeForAccessToken(code);
		oauthPlugin.accessApi(accessToken);
	}

	protected String exchangeCodeForAccessToken(String code) {
		return "";
	}

}
