package com.whenling.module.redis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.io.Closeables;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

public class RedisExample {

	public static void main(String[] args) {
		new RedisExample().testNormal();
	}

	// 普通同步方式
	public void testNormal() {// 12.526秒
		Jedis jedis = new Jedis("120.25.241.144", 6379);
		jedis.auth("b840fc02d52404542994");

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			jedis.set("n" + i, "n" + i);
			System.out.println(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		jedis.disconnect();
		try {
			Closeables.close(jedis, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 事务方式(Transactions)
	public void testTrans() {// 0.304秒
		Jedis jedis = new Jedis("120.25.241.144", 6379);
		jedis.auth("b840fc02d52404542994");

		long start = System.currentTimeMillis();
		Transaction tx = jedis.multi();
		for (int i = 0; i < 1000; i++) {
			tx.set("n" + i, "n" + i);
			System.out.println(i);
		}
		tx.exec();
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		jedis.disconnect();
		try {
			Closeables.close(jedis, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 管道(Pipelining)
	public void testPipelined() {// 0.076秒
		Jedis jedis = new Jedis("120.25.241.144", 6379);
		jedis.auth("b840fc02d52404542994");

		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		for (int i = 0; i < 1000; i++) {
			pipeline.set("n" + i, "n" + i);
			System.out.println(i);
		}
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		jedis.disconnect();
		try {
			Closeables.close(jedis, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 管道中调用事务
	public void testCombPipelineTrans() {// 0.099秒
		Jedis jedis = new Jedis("120.25.241.144", 6379);
		jedis.auth("b840fc02d52404542994");

		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for (int i = 0; i < 1000; i++) {
			pipeline.set("n" + i, "n" + i);
			System.out.println(i);
		}
		pipeline.exec();
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		jedis.disconnect();
		try {
			Closeables.close(jedis, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 分布式直连同步调用
	public void testShardNormal() {// 13.619秒
		JedisShardInfo jedis = new JedisShardInfo("120.25.241.144", 6379);
		jedis.setPassword("b840fc02d52404542994");

		List<JedisShardInfo> shards = Arrays.asList(jedis);
		ShardedJedis sharding = new ShardedJedis(shards);

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			sharding.set("n" + i, "n" + i);
			System.out.println(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		sharding.disconnect();
		try {
			Closeables.close(sharding, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 分布式直连异步调用
	public void testShardPipelined() {// 0.127秒
		JedisShardInfo jedis = new JedisShardInfo("120.25.241.144", 6379);
		jedis.setPassword("b840fc02d52404542994");

		List<JedisShardInfo> shards = Arrays.asList(jedis);
		ShardedJedis sharding = new ShardedJedis(shards);
		ShardedJedisPipeline pipeline = sharding.pipelined();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			pipeline.set("n" + i, "n" + i);
			System.out.println(i);
		}
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		sharding.disconnect();
		try {
			Closeables.close(sharding, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 分布式连接池同步调用
	public void testShardSimplePool() {// 12.642秒
		JedisShardInfo jedis = new JedisShardInfo("120.25.241.144", 6379);
		jedis.setPassword("b840fc02d52404542994");

		List<JedisShardInfo> shards = Arrays.asList(jedis);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), shards);

		ShardedJedis sharding = pool.getResource();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			sharding.set("n" + i, "n" + i);
			System.out.println(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		sharding.disconnect();
		pool.destroy();
		try {
			Closeables.close(sharding, true);
			Closeables.close(pool, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 分布式连接池异步调用
	public void testShardPipelinnedPool() {// 0.124秒
		JedisShardInfo jedis = new JedisShardInfo("120.25.241.144", 6379);
		jedis.setPassword("b840fc02d52404542994");

		List<JedisShardInfo> shards = Arrays.asList(jedis);
		ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), shards);

		ShardedJedis sharding = pool.getResource();
		ShardedJedisPipeline pipeline = sharding.pipelined();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			pipeline.set("n" + i, "n" + i);
			System.out.println(i);
		}
		pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("共花费：" + (end - start) / 1000.0 + "秒");

		sharding.disconnect();
		pool.destroy();

		try {
			Closeables.close(sharding, true);
			Closeables.close(pool, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
