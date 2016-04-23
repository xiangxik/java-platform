package com.whenling.centralize.support;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.whenling.centralize.Application;
import com.whenling.centralize.service.UserService;
import com.whenling.centralize.support.web.CurrentUserHandlerMethodArgumentResolver;
import com.whenling.module.security.captcha.CaptchaFilter;
import com.whenling.module.security.shiro.AjaxAuthenticationFilter;

@Configuration
public class ApplicationConfiguration {

	@Autowired
	private Collection<Realm> realms;

	@Autowired
	private org.apache.shiro.mgt.SecurityManager securityManager;

	@Autowired
	private UserService userService;

	@Autowired
	private CaptchaFilter captchaFilter;

	@PostConstruct
	public void init() {
		if (securityManager instanceof DefaultWebSecurityManager) {
			((DefaultWebSecurityManager) securityManager).setRealms(realms);
		}

		captchaFilter.getDistinguishPaths().add("/admin");
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/admin");
		shiroFilterFactoryBean.setSuccessUrl("/admin");
		shiroFilterFactoryBean.setUnauthorizedUrl("/error");

		Map<String, Filter> filters = Application.getSecurityfilters();
		filters.put(DefaultFilter.authc.name(), new AjaxAuthenticationFilter());
		shiroFilterFactoryBean.setFilters(filters);

		shiroFilterFactoryBean.setFilterChainDefinitionMap(Application.getSecurityFilterChainDefinitionMap());
		return shiroFilterFactoryBean;
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
