package com.lzb.rock.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * kafka消费者配置
 * 
 * @author Lvjiapeng
 *
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.consumer.servers}")
	private String servers;
	/**
	 * true kafka自动提交 false spring 自动提交
	 */
	@Value("${kafka.consumer.enable.auto.commit}")
	private boolean enableAutoCommit;
	/**
	 * 批量消费条数
	 */
	// @Value("${kafka.consumer.max.poll.records:10}")
//    private Integer maxPollRecords;
	@Value("${kafka.consumer.session.timeout}")
	private String sessionTimeout;
	@Value("${kafka.consumer.auto.commit.interval}")
	private String autoCommitInterval;
	@Value("${kafka.consumer.auto.offset.reset}")
	private String autoOffsetReset;
	@Value("${kafka.consumer.concurrency}")
	private Integer concurrency;

	@Value("${kafka.consumer.groupId}")
	private String groupId;

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(concurrency);
		factory.getContainerProperties().setPollTimeout(1500);
		factory.setBatchListener(false);// 设置不支持批量消费
		/**
		 * 手动提交，Acknowledgment.acknowledge 手动提交，需要enableAutoCommit 设置false
		 */
		// factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	public Map<String, Object> consumerConfigs() {
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		return propsMap;
	}

}
