package com.whenling.plugin.oauth.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.plugin.model.PluginModel;
import com.whenling.plugin.oauth.model.OauthPlugin;

@Controller
@RequestMapping("/admin/oauth/plugin")
public class OauthPluginController {

	@Autowired
	private List<OauthPlugin> oauthPlugins = new ArrayList<OauthPlugin>();

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<PluginModel> list(Pageable pageable) {
		oauthPlugins.sort(null);
		return new PageImpl<>(oauthPlugins).map(PluginModel::convert);
	}

}
