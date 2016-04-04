package com.whenling.extension.jms.mobile;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whenling.centralize.service.UserService;
import com.whenling.module.domain.model.Result;

@Controller
@RequestMapping("/mobile/captcha")
public class MobileCaptchaController {
	private Cache<String, Date> CACHE = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();

	private static final String MOBILE_PHONE_NUMBER_PATTERN = "^$|^0{0,1}(13[0-9]|15[0-9]|14[0-9]|17[0-9]|18[0-9])[0-9]{8}$";

	@Autowired
	private MobileCaptchaService mobileCaptchaService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result register(@RequestParam("phoneNumber") String phoneNumber) {
		boolean matched = Pattern.matches(MOBILE_PHONE_NUMBER_PATTERN, phoneNumber);

		if (matched) {
			if (userService.findByMobile(phoneNumber) != null) {
				return Result.exception().message("该手机号码已使用");
			}
			long seconds = -1l;
			Date date = CACHE.getIfPresent(phoneNumber);
			if (date == null) {
				try {
					mobileCaptchaService.sendCaptchaCode(phoneNumber);
				} catch (MobileCaptchaException e) {
					if (e instanceof ExceedMaxDailyTimesException) {
						return Result.exception().message("超出每天发送的最大次数");
					}
				}
				CACHE.put(phoneNumber, new Date());
			} else {
				Date now = new Date();
				seconds = (now.getTime() - date.getTime()) / 1000;
			}

			return Result.success().data(seconds);
		} else {
			return Result.exception().message("填写的手机号码有误");
		}

	}

}
