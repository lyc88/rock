package com.lzb.rock.redis.comfig;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置文件类
 * 
 * @author lzb https://blog.csdn.net/cadn_jueying/article/details/80736557
 */
public class RedisConfig extends CachingConfigurerSupport {
	private static Logger logger = Logger.getLogger(RedisConfig.class);

	@Bean
	@ConfigurationProperties(prefix = "spring.redis.pool")
	public JedisPoolConfig getRedisConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		logger.info("JedisConnectionFactory bean init success.");
		return factory;
	}

//	@Bean
//	@Primary
//	public RedisTemplate redisTemplate() {
//		// GenericJackson2JsonRedisSerializer、Jackson2JsonRedisSerializer是先将对象转为json，然后再保存到redis，所以，1在redis中是字符串1，所以无法进行加1
//		// 使用GenericToStringSerializer、StringRedisSerializer序列化器，都可以使用increment方法
//		JedisConnectionFactory jedisConnectionFactory = getConnectionFactory();
//		StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
//		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//				Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		template.afterPropertiesSet();
//		return template;
//	}

	@Bean
	@Primary
	public RedisTemplate<String, String> redisTemplate() {
		// GenericJackson2JsonRedisSerializer、Jackson2JsonRedisSerializer是先将对象转为json，然后再保存到redis，所以，1在redis中是字符串1，所以无法进行加1
		// 使用GenericToStringSerializer、StringRedisSerializer序列化器，都可以使用increment方法
		JedisConnectionFactory jedisConnectionFactory = getConnectionFactory();
		StringRedisTemplate redisTemplate = new StringRedisTemplate(jedisConnectionFactory);
		// key序列化方式,但是如果方法上有Long等非String类型的话，会报类型转换错误
		// 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();// Long类型不可以会出现异常信息;
		redisTemplate.setKeySerializer(redisSerializer);
		// redisTemplate.setHashKeySerializer(redisSerializer);
		// redisTemplate.setValueSerializer(redisSerializer);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

//	/**
//	 * 缓存管理器.
//	 * 
//	 * @param redisTemplate
//	 * @return
//	 */
//	@Bean
//	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
//		
//		
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		//设置超时时间，单位 秒
//		cacheManager.setDefaultExpiration(600L);
////		Map<String,Long> expiresMap=new HashMap<>();
////	    expiresMap.put("Product",5L);
////	    cacheManager.setExpires(expiresMap);
//		
//		return cacheManager;
//	}
//	/**
//	 * 自定义key生成策略 类名+方法名+参数(适用于分布式缓存)，默认key生成策略分布式下有可能重复被覆盖
//	 * 
//	 * @return
//	 */
//	@Bean
//	public KeyGenerator keyGenerator() {
//		return (o, method, objects) -> {
//			StringBuilder sb = new StringBuilder();
//			sb.append(o.getClass().getName());
//			sb.append("." + method.getName() + "(");
//			for (Object obj : objects) {
//				sb.append(obj.toString());
//			}
//			sb.append(")");
//			return sb.toString();
//		};
//	}
}