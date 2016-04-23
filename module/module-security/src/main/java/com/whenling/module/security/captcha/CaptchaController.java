package com.whenling.module.security.captcha;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.CaptchaService;

/**
 * 验证码控制器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:06:07
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public BufferedImage get(HttpServletRequest request, HttpServletResponse response) {
		return (BufferedImage) captchaService.getChallengeForID(request.getSession(true).getId());
	}

}
