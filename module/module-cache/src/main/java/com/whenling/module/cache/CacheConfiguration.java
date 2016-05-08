package com.whenling.module.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.whenling.module.cache.infinispan.provider.SpringEmbeddedCacheManagerFactoryBean;

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

	@Value("${cacheConfigFile?:/com/whenling/module/cache/infinispan/infinispan.xml}")
	private String cacheConfigFileLocation;

	@Bean
	public SpringEmbeddedCacheManagerFactoryBean springEmbeddedCacheManagerFactoryBean() {
		SpringEmbeddedCacheManagerFactoryBean cacheManagerFactoryBean = new SpringEmbeddedCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigurationFileLocation(new ClassPathResource(cacheConfigFileLocation));
		return cacheManagerFactoryBean;
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		try {
			return springEmbeddedCacheManagerFactoryBean().getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

	@Override
	public CacheResolver cacheResolver() {
		return new SimpleCacheResolver(cacheManager());
	}

}
