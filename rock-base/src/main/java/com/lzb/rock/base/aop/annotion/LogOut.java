package com.lzb.rock.base.aop.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.Order;


/**
 * 打印日志，sercice  controller 默认打印，加上会重复打印日志 日志AOP
 * 
 * @author lzb 2018年4月16日 下午4:46:11
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(998)
public @interface LogOut {
	boolean before() default true; // 默认打印入参

	boolean end() default true;// 默认打印返回值

	String name() default "";// 操作名称
}


