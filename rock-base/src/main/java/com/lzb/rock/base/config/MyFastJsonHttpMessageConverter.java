package com.lzb.rock.base.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * FastJsonHttpMessageConverter 配置公共类，所有方法采用同一个配置
 * https://blog.csdn.net/u010246789/article/details/52539576
 * 
 * @author lzb
 *
 *         2018年11月8日 上午8:51:11 此类不能用spring 实例化，不生效，问题待研究
 */
public class MyFastJsonHttpMessageConverter {

	private MyFastJsonHttpMessageConverter() {
	};

	private static FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

	/**
	 * 暂时未写单列 等后期项目新开的时候可以考虑 每次JSON的时候都会调用，，需要考虑单列
	 * 
	 * @return
	 */
	public static FastJsonHttpMessageConverter getInstance() {
		if (fastJsonHttpMessageConverter != null) {
			return fastJsonHttpMessageConverter;
		}
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 序列化值
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// fastJsonConfig.setDateFormat("yyyy-MM-dd
		// HH:mm:ss");//此处设置日期格式化，会让JSONField注解失效
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, // 结果是否格式化
				SerializerFeature.DisableCircularReferenceDetect, // 消除对同一对象循环引用的问题，默认为false
				SerializerFeature.WriteMapNullValue, // 输出去null值
				SerializerFeature.WriteNullListAsEmpty, // List字段如果为null,输出为[],而非null
				// SerializerFeature.WriteNullStringAsEmpty,//空值输出为空串
				SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null,输出为false,而非null
				SerializerFeature.WriteDateUseDateFormat // 设置日期格式化 yyyy-MM-dd HH:mm:ss
		);
		// 处理首字母大小写问题
		TypeUtils.compatibleWithJavaBean = true;
		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		fastJsonHttpMessageConverter = fastConverter;
		return fastJsonHttpMessageConverter;

	}
}
