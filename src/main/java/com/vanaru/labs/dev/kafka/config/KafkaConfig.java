/**
 * @author Varun
 *
 */
package com.vanaru.labs.dev.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.leave.app.dto.UserDTO;
import org.leave.app.kafka.constant.ApplicationConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


/**
 * @author Varun
 *
 */
@Configuration
@EnableKafka
public class KafkaConfig {
	
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //Key will be string in producer
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); //Value will be json object in producer
		return new DefaultKafkaProducerFactory<String, Object>(configMap);
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	/*@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);//we are providing apache kafka local server details
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//we are configuring what type of data for key we are are going to deserialize
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//we are configuring what type of data for value we are are going to deserialize
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_STRING);
		return new DefaultKafkaConsumerFactory<>(configMap);
	}*/
	
	@Bean
	public ConsumerFactory<String, UserDTO> consumerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);//we are providing apache kafka local server details
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//we are configuring what type of data for key we are are going to deserialize
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);//we are configuring what type of data for value we are are going to deserialize
		//configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//we are configuring what type of data for value we are are going to deserialize
		//configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_STRING);
		configMap.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationConstant.GROUP_ID_JSON);
		//serialization and deserialization can only be done by trusted sources
		configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "org.leave.app.dto");
		return new DefaultKafkaConsumerFactory<>(configMap);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserDTO> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UserDTO> factory = new ConcurrentKafkaListenerContainerFactory<String, UserDTO>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	/*@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}*/
}
