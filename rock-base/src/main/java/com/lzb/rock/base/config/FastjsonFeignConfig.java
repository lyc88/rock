package com.lzb.rock.base.config;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import feign.codec.Encoder;
import feign.codec.Decoder;
/**
 * https://blog.csdn.net/u011687186/article/details/78457723?locationNum=5&fps=1
 * @author admin
 * feign 配置Fastjson系列化方法
 *
 */
@Configuration
public class FastjsonFeignConfig {
	
    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(MyFastJsonHttpMessageConverter.getInstance());
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
    @Bean
    public Encoder feignEncoder(){
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(MyFastJsonHttpMessageConverter.getInstance());
        return new SpringEncoder(objectFactory);
    }
}
