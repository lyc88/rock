package com.lzb.rock.base.aop.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Order(999)
public @interface Log {
	boolean before() default true; // 默认打印入参

	boolean end() default true;// 默认打印返回值,false 不输出data
	
	boolean endAll() default true;// 默认打印返回值fasle 完全不输出返回日志

	String name() default "";// 操作名称
}


