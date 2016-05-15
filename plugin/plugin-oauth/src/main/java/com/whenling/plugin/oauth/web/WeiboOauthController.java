package com.whenling.plugin.oauth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.plugin.oauth.model.WeiboOauthPlugin;
import com.whenling.plugin.web.PluginController;

@Controller
@RequestMapping("/admin/oauth/weibo")
public class WeiboOauthController extends PluginController<WeiboOauthPlugin> {

}
