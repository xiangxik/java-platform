package com.whenling.module.security.captcha;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;
import com.octo.captcha.service.CaptchaService;
import com.whenling.module.base.SpringContext;
import com.whenling.module.security.RobotPrevention;

public class CaptchaRobotPrevention implements RobotPrevention {

	@Override
	public boolean validateRequest(ServletRequest request, ServletResponse response) {
		String captchaCode = request.getParameter("captcha");
		return !Strings.isNullOrEmpty(captchaCode) && SpringContext.getBean(CaptchaService.class)
				.validateResponseForID(((HttpServletRequest) request).getSession(true).getId(), captchaCode.toUpperCase());
	}

}
