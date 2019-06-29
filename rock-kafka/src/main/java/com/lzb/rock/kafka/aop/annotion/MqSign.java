package com.lzb.rock.kafka.aop.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lzb.rock.kafka.common.MqEnum;

/**
 * 日志AOP
 * 
 * @author lzb 2018年4月16日 下午4:46:11
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqSign {

	MqEnum value();

}
