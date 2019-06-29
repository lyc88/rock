package com.lzb.rock.base.config;

import feign.Request;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * feign 配置类
 * @author lzb
 *
 * 2018年12月7日 上午11:44:58
 */
@Configuration
@Slf4j
public class FeignConfigure {
	/**
	 * 请求处理的超时时间 单位毫秒
	 */
	@Value("${ribbon.ReadTimeout:5000}")
	private  Integer readTimeout;
	/**
	 * 请求连接的超时时间  单位毫秒
	 */
	@Value("${ribbon.ConnectTimeout:2000}")
	public  Integer connectTimeout;

	@Bean
	public Request.Options options() {
		log.info("FeignConfigure===============>readTimeout={};connectTimeout={}",readTimeout,connectTimeout);
		return new Request.Options(connectTimeout, readTimeout);
	}

//	@Bean
//	public Retryer feignRetryer() {
//		return new Retryer.Default();
//	}
	
//	 @Bean
//     public Retryer feignRetryer(){
//          Retryer retryer = new Retryer.Default(100, 1000, 4);
//          return retryer;
//     }
}
