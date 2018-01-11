package com.thinkgem.jeesite.common.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RedisPool属性配置类
 * <p>Title: RedisPoolConfig</p>
 * <p>Description: </p>
 * @author	OAO
 * @date	2017-11-18
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.pool")
public class RedisPoolConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer maxTotal;	// 最大分配的对象数
	private Integer maxIdle;	// 最大能够保持idel状态的对象数
	private Integer minIdle;	// 最小能够保持idel状态的对象数
	private Long maxWaitMillis;    // 连接池最大阻塞等待时间（使用负值表示没有限制）
	private boolean testOnBorrow;	// 当调用borrow Object方法时，是否进行有效性检查
	
	public Integer getMaxTotal() {
		return maxTotal;
	}
	
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public Integer getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public Integer getMinIdle() {
		return minIdle;
	}
	
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	
	public Long getMaxWaitMillis() {
		return maxWaitMillis;
	}
	
	public void setMaxWaitMillis(Long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	
	

}
