package com.lzb.rock.redis.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.util.UtilClass;
import com.lzb.rock.redis.aop.annotion.RedisDel;
import com.lzb.rock.redis.aop.annotion.RedisGet;
import com.lzb.rock.redis.mapper.RedisMapper;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
@Order(2)
@Slf4j
public class RedisGetAop {

	@Autowired
	RedisMapper redisMapper;

	@Pointcut(value = "@annotation(com.lzb.rock.redis.aop.annotion.RedisGet)")
	public void redisGet() {
	}

	//@Cacheable
	@Around("redisGet()")
	private Object handle(ProceedingJoinPoint point) throws Throwable {
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new RockException(ResultEnum.AOP_ERR, "该注解只能用于方法");
		}
		// 类完整路径
		String classType = point.getTarget().getClass().getName();
		msig = (MethodSignature) sig;
		Object target = point.getTarget();
		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		RedisGet redisGet = currentMethod.getAnnotation(RedisGet.class);
		String constant = redisGet.constant();
		String[] parameters = redisGet.parameters();
		long timeout = redisGet.timeout();
		// 获取参数列表
		// 方法名
		String methodName = currentMethod.getName();
		String className = point.getTarget().getClass().getName();
		Object[] params = point.getArgs();
		String[] names = UtilClass.getFieldsName(classType, methodName);
		String key = UtilRedisKey.getKey(constant, names, params, parameters);
		boolean flag = redisMapper.hasKey(key);
		Object rs = null;
		// 执行方法
		if (!flag) {
			rs = point.proceed();
			if (rs != null && StringUtils.isNotBlank(rs.toString())) {
				redisMapper.set(key, rs, timeout);
				log.debug("无缓存，刷新缓存,key：{}", key);
			}
		} else {
			Type returnType = currentMethod.getGenericReturnType();
			String str = redisMapper.get(key);
			rs=UtilClass.getJavaBeanByString(str, returnType);
			log.debug("读取缓存,key：{},value={}", key,str);
		}
		return rs;
	}
}
