package org.shinomin.commons.web.cache.impl;

import org.shinomin.commons.cache.Cache;
import org.shinomin.commons.cache.impl.CacheImpl;

/**
 * 系统公共缓存管理,单例模式
 * 
 * @author hjin
 * 
 */
public class ContextInitCache implements Cache
{

	private static int Max_Size = 200; // cache的最大值

	private static ContextInitCache instance = new ContextInitCache(); // PublicCache的实例，单例摸式

	private Cache _cache; // cache类

	private ContextInitCache()
	{
		_cache = new CacheImpl(Max_Size);
	}

	public static ContextInitCache getInstance()
	{
		return instance;
	}

	public synchronized void cache(Object cacheKey, Object obj)
	{
		_cache.cache(cacheKey, obj);
	}

	public Object getCachedData(Object cacheKey)
	{
		return _cache.getCachedData(cacheKey);
	}

	public long getCachedSize()
	{
		return _cache.getCachedSize();
	}

	public synchronized Object remove(Object cacheKey)
	{
		return _cache.remove(cacheKey);
	}

	public synchronized void clear()
	{
		_cache.clear();
	}

	public boolean contains(Object cacheKey)
	{
		return _cache.contains(cacheKey);
	}

}
