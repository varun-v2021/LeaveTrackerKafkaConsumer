/**
 * @author Varun
 *
 */
package com.vanaru.labs.dev.kafka.consumer;

import org.leave.app.dto.UserDTO;
import org.leave.app.kafka.constant.ApplicationConstant;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Varun
 *
 */
@Component
public class KafkaConsumer {
	/*@KafkaListener(groupId = ApplicationConstant.GROUP_ID_STRING, topics = ApplicationConstant.TOPIC_NAME, containerFactory = ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY)
	public void receivedMessage(String message) {
		System.out.println("Message Received using Kafka listener " + message);
	}*/
	
	@KafkaListener(groupId = ApplicationConstant.GROUP_ID_JSON, topics = ApplicationConstant.TOPIC_NAME, containerFactory = ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY)
	public void receivedJson(UserDTO userDTO) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(userDTO);
		System.out.println("Json message received using Independent Kafka listener " + jsonString);
	}
}
