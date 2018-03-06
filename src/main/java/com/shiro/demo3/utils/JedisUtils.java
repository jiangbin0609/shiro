package com.shiro.demo3.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**   
 * @description  redis连接工具类
 * @author intelink012   
 * @date 2018年3月6日 上午9:12:01 
 * @version 1.0.0  
 */
public class JedisUtils {

	private static JedisPool jedisPool;

	static {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxWaitMillis(100);
		jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
	}

	/** 
	 * @description 获得连接
	 * @return
	 * @exception
	 * @author intelink012
	 * @date 2018年3月6日 上午9:11:57
	 * @version 1.0.0
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	/** 
	 * @description 关闭连接
	 * @param jedis
	 * @exception
	 * @author intelink012
	 * @date 2018年3月6日 上午9:12:26
	 * @version 1.0.0
	 */
	public static void close(Jedis jedis) {
		jedis.close();
	}
}
