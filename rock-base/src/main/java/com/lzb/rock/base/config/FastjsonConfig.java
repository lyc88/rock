package com.lzb.rock.base.config;


import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * fastjson 定义
 * 
 * @author lzb
 *
 *         2018年11月7日 下午4:04:55
 */
@Configuration
public class FastjsonConfig {

	// 如果采用注解这种方式，感觉都可以不用放在这个地方
	// 只要在spring容器启动的时候被扫描到就行了
	// @Bean
//	public HttpMessageConverters fastJsonConfigure() {
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		// 日期格式化
//		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		converter.setFastJsonConfig(fastJsonConfig);
//		return new HttpMessageConverters(converter);
//	}

	// 序列化配置
	@Bean
	public HttpMessageConverters fastjsonHttpMessageConverters() {

		return new HttpMessageConverters(MyFastJsonHttpMessageConverter.getInstance());

	}
}