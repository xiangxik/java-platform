package com.whenling.module.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig());
		connectionFactory.setHostName("120.25.241.144");
		connectionFactory.setPort(6379);
		connectionFactory.setPassword("b840fc02d52404542994");
		return connectionFactory;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
		return stringRedisTemplate;
	}
}
