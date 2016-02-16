package com.whenling.core.support.security;

import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

import net.sf.ehcache.CacheManager;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private CacheManager cacheManager;

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/admin");
		shiroFilterFactoryBean.setSuccessUrl("/admin");
		shiroFilterFactoryBean.setUnauthorizedUrl("/error");

		Map<String, Filter> filters = Maps.newHashMap();
		filters.put(DefaultFilter.authc.name(), authc());
		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> filterChainDefinitionMap = Maps.newHashMap();
		filterChainDefinitionMap.put("/admin", DefaultFilter.authc.name());
		filterChainDefinitionMap.put("/admin/**", DefaultFilter.authc.name());
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public AjaxAuthenticationFilter authc() {
		return new AjaxAuthenticationFilter();
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setCacheManager(shiroCacheManager());
		// securityManager.setSessionManager(sessionManager());
		securityManager.setRealm(databaseRealm());
		return securityManager;
	}

	@Bean
	public DatabaseRealm databaseRealm() {
		return new DatabaseRealm();
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public org.apache.shiro.cache.CacheManager shiroCacheManager() {
		EhCacheManager shiroCacheManager = new EhCacheManager();
		shiroCacheManager.setCacheManager(cacheManager);
		return shiroCacheManager;
	}

}
