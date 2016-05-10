package com.whenling.plugin.oauth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.module.web.controller.BaseController;

@Controller
@RequestMapping("/oauth")
public class OauthController extends BaseController {

	public void redirectUsersToRequestAccess() {

	}

	public void redirectBack(String code) {

	}

	public String exchangeCodeForAccessToken(String code) {
		return "";
	}

	public void accessApi(String accessToken) {

	}

}
