package com.whenling.module.security.shiro.cache.infinispan;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.infinispan.commons.api.BasicCache;
import org.springframework.util.Assert;

public class ShiroCache<K, V> implements Cache<K, V> {

	private final BasicCache<K, V> nativeCache;

	public ShiroCache(final BasicCache<K, V> nativeCache) {
		Assert.notNull(nativeCache, "A non-null Infinispan cache implementation is required");
		this.nativeCache = nativeCache;
	}

	@Override
	public V get(K key) throws CacheException {
		return this.nativeCache.get(key);
	}

	@Override
	public V put(K key, V value) throws CacheException {
		return this.nativeCache.put(key, value);
	}

	@Override
	public V remove(K key) throws CacheException {
		return this.nativeCache.remove(key);
	}

	@Override
	public void clear() throws CacheException {
		this.nativeCache.clear();
	}

	@Override
	public int size() {
		return this.nativeCache.size();
	}

	@Override
	public Set<K> keys() {
		return this.nativeCache.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.nativeCache.values();
	}

}
