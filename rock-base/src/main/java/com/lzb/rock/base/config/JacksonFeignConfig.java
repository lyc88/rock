package com.lzb.rock.base.config;
//package com.jiahong.base.config;
//
//import java.text.DateFormat;
//
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
//import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
//import org.springframework.cloud.netflix.feign.support.SpringDecoder;
//import org.springframework.cloud.netflix.feign.support.SpringEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import feign.codec.Encoder;
//import feign.codec.Decoder;
///**
// * https://blog.csdn.net/u011687186/article/details/78457723?locationNum=5&fps=1
// * @author admin
// * feign 配置ackson系列化方法
// *
// */
////@Configuration
//public class JacksonFeignConfig {
//  //  @Bean
//    public Decoder feignDecoder() {
//        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
//        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
//        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
//    }
//   // @Bean
//    public Encoder feignEncoder(){
//        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
//        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
//        return new SpringEncoder(objectFactory);
//    }
// 
//    public ObjectMapper customObjectMapper(){
//    	//兼容日期格式yyyy-MM-dd HH:mm:ss
//        ObjectMapper objectMapper = new ObjectMapper();
//        DateFormat dateDormat = objectMapper.getDateFormat();
//        objectMapper.setDateFormat(new MyDateFormat(dateDormat));
//        //Customize as much as you want
//     // 视空字符传为null
//        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//        return objectMapper;
//    }
//}
