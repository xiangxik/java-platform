package com.whenling.core.support.captcha;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.gimpy.SimpleListImageCaptchaEngine;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

@Configuration
public class CaptchaConfiguration {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CaptchaService captchaService() {
		DefaultManageableImageCaptchaService captchaService = new DefaultManageableImageCaptchaService();
		captchaService.setCaptchaEngine(captchaEngine());
		return captchaService;
	}

	@Bean
	public CaptchaEngine captchaEngine() {
		SimpleListImageCaptchaEngine captchaEngine = new SimpleListImageCaptchaEngine();
		return captchaEngine;
	}

	@Bean
	public WebMvcConfigurer captchaWebMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter = new BufferedImageHttpMessageConverter();
				if (converters.size() > 0) {
					converters.add(converters.size() - 1, bufferedImageHttpMessageConverter);
				} else {
					converters.add(bufferedImageHttpMessageConverter);
				}
			}
		};
	}
}
