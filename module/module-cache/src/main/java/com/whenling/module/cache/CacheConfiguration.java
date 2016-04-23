package com.whenling.module.cache;

import org.springframework.beans.factory.annotation.Value;
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

/**
 * 缓存配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:50:24
 */
@Configuration
@EnableCaching
public class CacheConfiguration implements CachingConfigurer {

	@Value("${cacheConfigFile?:com/whenling/module/cache/ehcache.xml}")
	private String cacheConfigFileLocation;

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource(cacheConfigFileLocation));
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
