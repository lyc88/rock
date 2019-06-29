package com.lzb.rock.base.aop.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;

/**
 * 日志AOP
 * 
 * @author lzb 2018年4月16日 下午4:46:11
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sign {
	/**********************
	 * 秘钥规则
	 * 校验签名
	 * checkSignKey 为空，使用配置文件checkSignKey,都为空，抛异常
	 * 返回值添加签名
	 * signKey 为空使用 配置文件 signKey,配置文件为空signKey为空使用注解中checkSignKey,注解中checkSignKey为空，使用配置文件中checkSignKey,都为空，抛异常
	 */
	/**
	 * 校验签名秘钥
	 * 不填为 baseSignKey
	 * @return
	 */
	String checkSignKey() default ""; 
	/**
	 * 签名秘钥
	 * 不填为 baseSignKey
	 * @return
	 */
	String signKey() default ""; 
	/**
	 * 默认校验入参签名
	 * @return
	 */
	boolean checkSign() default true;
	/**
	 * 默认返回值添加签名
	 * @return
	 */
	boolean sign() default true;
	/**
	 * 校验参数名称数组,不填则为全部
	 * @return
	 */
	String[] checkNames() default {};

}
