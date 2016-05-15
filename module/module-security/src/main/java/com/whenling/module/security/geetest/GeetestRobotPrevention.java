package com.whenling.module.security.geetest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.whenling.module.security.RobotPrevention;

public class GeetestRobotPrevention implements RobotPrevention {

	@Override
	public boolean validateRequest(ServletRequest request, ServletResponse response) {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.captchaId, GeetestConfig.privateKey);

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		// 从session中获取gt-server状态
		int gt_server_status_code = (Integer) httpRequest.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

		// 从session中获取userid
		String userid = (String) httpRequest.getSession().getAttribute("userid");
		int gtResult = 0;

		String challenge = httpRequest.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = httpRequest.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = httpRequest.getParameter(GeetestLib.fn_geetest_seccode);

		if (gt_server_status_code == 1) {
			// gt-server正常，向gt-server进行二次验证

			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
		} else {
			// gt-server非正常情况下，进行failback模式验证

			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
		}

		if (gtResult == 1) {
			// 验证成功
			return true;
		} else {
			// 验证失败
			return false;
		}
	}

}
