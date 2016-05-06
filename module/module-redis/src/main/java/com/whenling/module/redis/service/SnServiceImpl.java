package com.whenling.module.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SnServiceImpl implements SnService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public long nextValue(String type) {
		return stringRedisTemplate.execute((RedisCallback<Long>) action -> {
			byte[] typeKey = type.getBytes();
			return action.incr(typeKey);
		});
	}

}
