package com.whenling.module.security.shiro.cache.infinispan;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;

import com.whenling.module.cache.infinispan.provider.SpringCache;
import com.whenling.module.cache.infinispan.provider.SpringEmbeddedCacheManager;

public class ShiroCacheManager extends AbstractCacheManager {

	@Autowired
	private SpringEmbeddedCacheManager springEmbeddedCacheManager;

	@Override
	protected Cache<?, ?> createCache(String name) throws CacheException {
		SpringCache springCache = springEmbeddedCacheManager.getCache(name);
		return new ShiroCache<>(springCache.getNativeCache());
	}

}
