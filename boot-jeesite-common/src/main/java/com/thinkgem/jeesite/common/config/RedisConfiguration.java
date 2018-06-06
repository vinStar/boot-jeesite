package com.thinkgem.jeesite.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置类
 * <p>Title: RedisConfiguration</p>
 * <p>Description: </p>
 * @author	OAO
 * @date	2017-11-18
 * @version 1.0
 */
@Configuration
@AutoConfigureAfter(RedisPoolConfig.class)
public class RedisConfiguration {
	
	@Autowired
	private RedisPoolConfig poolConfig;
	
	/**
	 * redis数据库索引（默认为0）
	 */
	@Value("${spring.redis.database}")
	private int database;
	
	/**
	 * redis服务器地址
	 */
	@Value("${spring.redis.host}")
	private String host;
	
	/**
	 * redis服务器连接端口
	 */
	@Value("${spring.redis.port}")
	private int port;
	
	/**
	 * 连接超时时间（毫秒）
	 */
	@Value("${spring.redis.timeout}")
    private int timeout;
	
	/**
	 * Redis服务器连接密码（默认为空）
	 */
	@Value("${spring.redis.password:redis}")
	private String password;
	
	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig bean = new JedisPoolConfig();
		bean.setMaxIdle(poolConfig.getMaxIdle());
		bean.setMaxTotal(poolConfig.getMaxTotal());
		bean.setTestOnBorrow(poolConfig.isTestOnBorrow());
		return bean;
	}
	
	@Bean(name = "jedisPool")
	public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
		JedisPool bean = new JedisPool(jedisPoolConfig, host, port);
		return bean;
	}
}
