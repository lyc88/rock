package com.lzb.rock.redis.aop.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询数据，缓存有取缓存，没有查询数据库，同时更新缓存
 * @author lzb
 * 2018年7月11日 上午10:22:43
 */

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisGet {
	/**
	 * key 生成常量
	 * @return
	 */
	String constant();
	/**
	 * 参数名称
	 * @return
	 */
    String[] parameters() default {};//参数
    /**
     * 超时时间，默认600秒
     * @return
     */
    long timeout() default 600L;
} 
