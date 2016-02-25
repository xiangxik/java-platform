package com.whenling.core.support.security;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.collect.Maps;

import net.sf.ehcache.CacheManager;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private CacheManager cacheManager;

	@Value("#{T(org.apache.shiro.codec.Base64).decode('asdqwe123')}")
	private byte[] cipherKey;

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
		filterChainDefinitionMap.put("/admin/logout", DefaultFilter.logout.name());
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
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setRealm(databaseRealm());
		return securityManager;
	}

	@Bean
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey(cipherKey);
		rememberMeManager.setCookie(rememberMeCookie());
		return rememberMeManager;
	}

	@Bean
	public Cookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(31536000);
		return cookie;
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1000 * 60 * 30);
		sessionManager.setDeleteInvalidSessions(true);
		// sessionManager.setSessionValidationSchedulerEnabled(false);
		// sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}

	@Bean
	public SessionDAO sessionDAO() {
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		sessionDAO.setActiveSessionsCacheName("shiro-activieSessionCache");
		sessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return sessionDAO;
	}

	@Bean
	public SessionIdGenerator sessionIdGenerator() {
		JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
		return sessionIdGenerator;
	}

	@Bean
	public Cookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setMaxAge(-1);
		return cookie;
	}

	@Bean
	public MethodInvokingFactoryBean setSecurityManager() {
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		factoryBean.setArguments(new Object[] { securityManager() });
		return factoryBean;
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

	@Bean
	public WebMvcConfigurer securityWebMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
			}
		};
	}

	@Bean
	public HandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
		return new CurrentUserHandlerMethodArgumentResolver();
	}
}
