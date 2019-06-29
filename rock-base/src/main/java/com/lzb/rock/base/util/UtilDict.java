/**
 * @author Administrator
 *
 * 
 *2019年6月13日 下午11:00:28
 */
package com.lzb.rock.base.util;

import java.lang.reflect.Field;

import com.lzb.rock.base.aop.annotion.DictKey;
import com.lzb.rock.base.busInterface.IDictFormatter;
import com.lzb.rock.base.common.ResultEnum;
import com.lzb.rock.base.exception.RockException;
import com.lzb.rock.base.exception.UtilExceptionStackTrace;
import com.lzb.rock.base.holder.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LZB 字典工具类
 * 
 *         2019年6月13日 下午11:00:28
 */
@Slf4j
public class UtilDict {

	/**
	 * 字典key转value
	 * 
	 * @param obj
	 */
	public static void decoder(Object obj) {

		if (obj == null) {
			return;
		}
		// 得到类对象
		Class<? extends Object> userCla = (Class<? extends Object>) obj.getClass();
		// 得到类中的所有属性集合
		Field[] fs = userCla.getDeclaredFields();
		for (Field field : fs) {
			field.setAccessible(true); // 设置些属性是可以访问的
			/**
			 * 判断是否有字典转换注解
			 */
			if (field.isAnnotationPresent(DictKey.class)) {
				DictKey dictKey = field.getAnnotation(DictKey.class);
				boolean isCover = dictKey.isCover();
				String valueFiled = dictKey.valueFiled();
				Class<? extends IDictFormatter> dict = dictKey.dict();
				String code = dictKey.code();

				Object key = null;
				try {
					key = field.get(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (key != null) {
					IDictFormatter formatter = SpringContextHolder.getBean(dictKey.dict());
					// IDictFormatter formatter=new TestDictFormatter();
					if (formatter == null) {
						throw new RockException(ResultEnum.AOP_ERR, "字典格式化类获取失败", "");
					}

					try {
						Field valueField = userCla.getDeclaredField(valueFiled);
						valueField.setAccessible(true); // 设置些属性是可以访问的
						// 判断是否需要注入
						if (!isCover) {
							Object valueOld = valueField.get(obj);
							if (valueOld != null) {
								continue;
							}
							if (valueOld instanceof String) {
								if (UtilString.isNotBlank(valueOld.toString())) {
									continue;
								}
							}
						}
						// 转换注入
						String value = "";
						if (key instanceof String) {
							value = formatter.getValue(code, key.toString());
						} else if (key instanceof Long) {
							value = formatter.getValue(code, (Long) key);
						} else {
							// 强行转换long
							if (UtilString.isNumeric(key.toString())) {
								value = formatter.getValue(code, Long.valueOf(key.toString()));
							} else {
								throw new RockException(ResultEnum.AOP_ERR, "字典转换key类型不匹配", "");
							}

						}
						valueField.set(obj, value);
					} catch (Exception e) {
						log.error(UtilExceptionStackTrace.getStackTrace(e));
					}

				}
				System.out.println(field.getName() + "=" + key);
				System.out.println(dictKey.code());
				System.out.println(dictKey.isCover());
				System.out.println(dictKey.valueFiled());
				System.out.println(dictKey.dict());
			}
		}

	}

}
