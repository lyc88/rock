package com.lzb.rock.base.aop.annotion;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lzb.rock.base.busInterface.IDictFormatter;
import com.lzb.rock.base.busInterface.ISysDictFormatter;

/**
 * 字典转换类
 * 
 * @return
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictKey {
	/**
	 * 字典编码
	 * 
	 * @return
	 */
	String code();

	/**
	 * value 存储字段名
	 * 
	 * @return
	 */
	String valueFiled();

	/**
	 * 翻译实现类
	 * 
	 * @return
	 */
	Class<? extends IDictFormatter> dict() default ISysDictFormatter.class;

	/**
	 * value 不为空的时候是否覆盖
	 * 
	 * @return
	 */
	boolean isCover() default false;

}
