package com.shiro.demo3.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;

/**   
 * @description  自定义的redis cache
 * @author intelink012   
 * @date 2018年3月6日 上午9:30:56 
 * @version 1.0.0  
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

	// 清除缓存
	@Override
	public void clear() throws CacheException {
		JedisUtils.getJedis().flushDB();
	}

	// 获取缓存
	@Override
	public Object get(Object key) throws CacheException {
		byte[] bs = SerializationUtils.serialize((Serializable) key);
		byte[] value = JedisUtils.getJedis().get(bs);
		if (value == null) {
			return null;
		}
		return SerializationUtils.deserialize(value);
	}

	// 获取所有的key
	@Override
	public Set keys() {
		Set<byte[]> keys = JedisUtils.getJedis().keys(new String("*").getBytes());
		Set<Object> set = new HashSet<>();
		for (byte[] bs : keys) {
			set.add(SerializationUtils.deserialize(bs));
		}
		return set;
	}

	// 添加缓存
	@Override
	public Object put(Object key, Object value) throws CacheException {
		Jedis jedis = JedisUtils.getJedis();
		jedis.set(SerializationUtils.serialize((Serializable) key), SerializationUtils.serialize((Serializable) value));
		byte[] bs = jedis.get(SerializationUtils.serialize((Serializable) key));
		Object object = SerializationUtils.serialize(bs);
		return object;
	}

	// 移除缓存
	@Override
	public Object remove(Object key) throws CacheException {
		Jedis jedis = JedisUtils.getJedis();
		byte[] bs = jedis.get(SerializationUtils.serialize((Serializable) key));
		jedis.del(SerializationUtils.serialize((Serializable) key));

		return SerializationUtils.deserialize(bs);
	}

	// 获取缓存的个数
	@Override
	public int size() {
		Long size = JedisUtils.getJedis().dbSize();
		return size.intValue();
	}

	// 获取所有的value
	@Override
	public Collection values() {
		Set keys = this.keys();
		ArrayList<Object> values = new ArrayList<>();

		for (Object object : keys) {
			byte[] bytes = JedisUtils.getJedis().get(SerializationUtils.serialize((Serializable) object));
			values.add(SerializationUtils.deserialize(bytes));
		}
		return values;
	}

}
