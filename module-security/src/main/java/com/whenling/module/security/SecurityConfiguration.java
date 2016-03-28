package com.whenling.module.security;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
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

import net.sf.ehcache.CacheManager;

/**
 * 安全配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:08:55
 */
@Configuration
public class SecurityConfiguration {

	@Autowired
	private CacheManager cacheManager;

	@Value("#{T(org.apache.shiro.codec.Base64).decode('asdqwe123')}")
	private byte[] cipherKey;

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setCacheManager(shiroCacheManager());
		// securityManager.setSessionManager(sessionManager());
		securityManager.setRememberMeManager(rememberMeManager());
		// securityManager.setRealm(databaseRealm());
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
