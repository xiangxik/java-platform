package com.whenling.core.support.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfiguration implements CachingConfigurer {

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("/com/whenling/core/support/cache/ehcache.xml"));
		factoryBean.setShared(true);
		return factoryBean;
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		EhCacheCacheManager springCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
		springCacheManager.setTransactionAware(true);
		return springCacheManager;
	}

	@Override
	public CacheResolver cacheResolver() {
		return new EhCacheCacheResolver((EhCacheCacheManager) cacheManager());
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new BaseKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

}
