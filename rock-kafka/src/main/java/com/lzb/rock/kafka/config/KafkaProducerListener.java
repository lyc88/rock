package com.lzb.rock.kafka.config;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import com.lzb.rock.base.exception.UtilExceptionStackTrace;

import lombok.extern.slf4j.Slf4j;
/**
 * 消息发送后回调基类
 * @author lzb
 *
 * 2018年12月17日 下午2:07:07
 */
@Component
@Slf4j
public class KafkaProducerListener implements ProducerListener<String, String> {
	@Override
	public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
		//log.info("onSuccess=======>topic={};partition={};key={};value={};recordMetadata={}",topic,partition,key,value,recordMetadata);
	}

	@Override
	public void onError(String topic, Integer partition, String key, String value, Exception exception) {
		log.error("消息发送失败=======>topic={};partition={};key={};value={};exception={}",topic,partition,key,value,UtilExceptionStackTrace.getStackTrace(exception));
	}

	@Override
	public boolean isInterestedInSuccess() {
		
		return false;
	}

}
