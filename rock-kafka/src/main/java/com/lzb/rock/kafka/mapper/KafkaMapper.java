package com.lzb.rock.kafka.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.lzb.rock.base.util.UtilJson;
import com.lzb.rock.base.util.UtilSign;
import com.lzb.rock.kafka.common.MqEnum;

/**
 * kafka消息发送基类
 * 
 * @author lzb
 *
 *         2018年12月17日 下午2:27:07
 */
@Component
public class KafkaMapper {

	@Autowired
	private KafkaTemplate<String, String> KafkaTemplate;

	/**
	 * 发送list消息
	 * 
	 * @param mqEnum
	 * @param data
	 * @param isSync
	 * @return
	 */
	public ListenableFuture<SendResult<String, String>> send(MqEnum mqEnum, List<?> data, Boolean isSync) {

		String key = UtilSign.sign(data, mqEnum.getSginkey());
		String value = UtilJson.getStr(data);
		return send(mqEnum.getTopic(), key, value, isSync);

	}

	/**
	 * 发送object消息
	 * 
	 * @param mqEnum
	 * @param data
	 * @param isSync
	 * @return
	 */
	public ListenableFuture<SendResult<String, String>> send(MqEnum mqEnum, Object data, Boolean isSync) {

		String key = UtilSign.sign(data, mqEnum.getSginkey());
		String value = UtilJson.getStr(data);
		return send(mqEnum.getTopic(), key, value, isSync);

	}

	/**
	 * 发送object消息
	 * 
	 * @param mqEnum
	 * @param data
	 * @param isSync
	 * @return
	 */
	public ListenableFuture<SendResult<String, String>> send(MqEnum mqEnum, Boolean isSync) {

		return send(mqEnum.getTopic(), null, null, isSync);

	}

	/**
	 * 消息发送基类，不可修改
	 * 
	 * @param        <T>
	 * 
	 * @param mqEnum 消息定义枚举
	 * @param data   消息体
	 * @param isSync 是否同步，true 同步
	 * @return
	 */
	private ListenableFuture<SendResult<String, String>> send(String topic, String key, String value, Boolean isSync) {

		ListenableFuture<SendResult<String, String>> rs = KafkaTemplate.send(topic, key, value);
		if (isSync) {
			try {
				rs.get();
			} catch (Exception e) {
				rs = null;
				e.printStackTrace();
			}
		}
		return rs;
	}

}
