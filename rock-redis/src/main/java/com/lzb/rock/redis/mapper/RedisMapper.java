package com.lzb.rock.redis.mapper;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.lzb.rock.base.util.UtilClass;

@Component
public class RedisMapper {
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	/**
	 * redis set缓存
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * redis set缓存 失效时间默认600秒
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		set(key, value, 600);
	}

	/**
	 * 
	 * @param key
	 * @param obj 只能为对象
	 */
	public void set(String key, Object obj) {
		set(key, JSON.toJSONString(obj), 600);
	}

	/**
	 * 
	 * @param key
	 * @param obj     只能为对象
	 * @param timeout
	 */
	public void set(String key, Object obj, long timeout) {

		set(key, JSON.toJSONString(obj), timeout);
	}

	/**
	 * 判断缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获取过期时间
	 * 
	 * @param key
	 * @return
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);// 根据key获取过期时间
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key, Class<T> targetClass) {
		String str = redisTemplate.opsForValue().get(key);
		return (T) UtilClass.getJavaBeanByString(str, targetClass);
	}

	/**
	 * 对缓存数加减
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long increment(String key, long delta) {

		return redisTemplate.boundValueOps(key).increment(delta);
	}

	/**
	 * 对缓存数加减
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public Double increment(String key, Double delta) {

		return redisTemplate.boundValueOps(key).increment(delta);
	}

	/**
	 * 判断锁是否存在
	 */
	public boolean islock(String key) {
		key = getLockKey(key);
		return redisTemplate.hasKey(key);
	}

	/**
	 * 加锁 默认有效时间5分钟
	 * 
	 * @param key 锁唯一id
	 * @return
	 */
	public boolean lock(String key) {
		key = getLockKey(key);
		return lock(key, 60 * 5);
	}

	/**
	 * 重新设置锁的失效时间
	 * 
	 * @param key
	 * @param timeout
	 */
	public void lockExpire(String key, long timeout) {
		key = getLockKey(key);
		if (redisTemplate.hasKey(key)) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 加锁
	 * 
	 * @param key     锁唯一id
	 * @param timeout 超时时间，单位秒
	 * @return true成功， false失败
	 */
	public boolean lock(String key, long timeout) {
		key = getLockKey(key);
		Boolean isLock = false;
		String uuid = UUID.randomUUID().toString();
		// 若不在，则设置值
		Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, uuid);
		if (flag && redisTemplate.hasKey(key)) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);

			String newUUID = redisTemplate.opsForValue().get(key);

			if (uuid.equals(newUUID)) {
				isLock = true;
			}
		}
		return isLock;
	}

	/**
	 * 解锁
	 */
	public void unlock(String key) {
		key = getLockKey(key);
		redisTemplate.delete(key);
	}

	/**
	 * 生成分布式锁的key
	 * 
	 * @return
	 */
	private String getLockKey(String key) {
		if (!key.startsWith("lock_")) {
			key = "lock_" + key;
		}
		return key;
	}
}
