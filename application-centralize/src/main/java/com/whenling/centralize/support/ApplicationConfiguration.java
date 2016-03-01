package com.whenling.centralize.support;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.whenling.centralize.service.UserService;
import com.whenling.centralize.support.web.CurrentUserHandlerMethodArgumentResolver;

@Component
public class ApplicationConfiguration {

	@Autowired
	private Collection<Realm> realms;

	@Autowired
	private org.apache.shiro.mgt.SecurityManager securityManager;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		if (securityManager instanceof DefaultWebSecurityManager) {
			((DefaultWebSecurityManager) securityManager).setRealms(realms);
		}
	}

	@Bean
	public WebMvcConfigurer securityWebMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				argumentResolvers.add(new CurrentUserHandlerMethodArgumentResolver(userService));
			}
		};
	}

}
