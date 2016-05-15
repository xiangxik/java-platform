package com.whenling.module.security.geetest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/geetest")
public class GeetestController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String startCaptcha(HttpServletRequest request) {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.captchaId, GeetestConfig.privateKey);

		String userid = request.getSession().getId();
		int gtServerStatus = gtSdk.preProcess(userid);
		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		request.getSession().setAttribute("userid", userid);

		String resStr = gtSdk.getResponseStr();
		return resStr;
	}
}
