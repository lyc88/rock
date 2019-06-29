package com.lzb.rock.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * kafka生产者配置文件
 * 
 * @author lzb
 *
 *         2018年12月14日 下午2:08:24
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Value("${kafka.producer.servers}")
	private String servers;
	@Value("${kafka.producer.retries}")
	private int retries;
	@Value("${kafka.producer.batch.size}")
	private int batchSize;
	@Value("${kafka.producer.linger}")
	private int linger;
	@Value("${kafka.producer.buffer.memory}")
	private int bufferMemory;

	@Autowired
	KafkaProducerListener kafkaProducerListener;
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1);
		return props;
	}

	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
		kafkaTemplate.setProducerListener(kafkaProducerListener);
		return  kafkaTemplate;
	}

}
