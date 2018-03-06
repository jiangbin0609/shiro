package com.shiro.demo3.utils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

public class CustomShiroCacheManager implements CacheManager {

	@Override
	public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
		return new ShiroRedisCache<>();
	}
}
