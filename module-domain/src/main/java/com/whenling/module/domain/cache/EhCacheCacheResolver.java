package com.whenling.module.domain.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import com.google.common.collect.Lists;
import com.whenling.module.domain.service.BaseService;

/**
 * 缓存解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:50:31
 */
public class EhCacheCacheResolver implements CacheResolver {

	private EhCacheCacheManager cacheManager;

	public EhCacheCacheResolver(EhCacheCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
		Collection<String> cacheNames = getCacheNames(context);
		if (cacheNames == null) {
			return Collections.emptyList();
		} else {
			Collection<Cache> result = new ArrayList<Cache>();
			for (String cacheName : cacheNames) {
				Cache cache = this.cacheManager.getCache(cacheName);
				if (cache == null) {
					this.cacheManager.getCacheManager().addCache(cacheName);
				}
				result.add(cache);
			}
			return result;
		}
	}

	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		Object target = context.getTarget();
		if (target instanceof BaseService) {
			return Lists.newArrayList(((BaseService<?, ?>) target).getEntityClass().getName());
		}
		return context.getOperation().getCacheNames();
	}

}
