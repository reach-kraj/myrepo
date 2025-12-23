//package com.example.demo.publisher;
//
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class DemoRedisMessagePublisher {
//	
//	String channelName = "REDIS-CHANNEL";
//	
//
//	 private final StringRedisTemplate redisTemplate;
//	
//
//	    public DemoRedisMessagePublisher(StringRedisTemplate redisTemplate) {
//	        this.redisTemplate = redisTemplate;
//	    }
//	   
//	    public void publishMessage(String message) {
//	        redisTemplate.convertAndSend(channelName, message);
//	    }
//	    
//		 @PostConstruct
//			public void init() throws Exception {
//				DemoMyMessage message = new DemoMyMessage();
//				message.setNotificationId(2);
//				message.setSource("Test-source");
//				ObjectMapper mapper = new ObjectMapper();
//				String json = mapper.writeValueAsString(message);
//				publishMessage(json);
//				System.out.println("Message published to channel");
//			}
//
//}
//
package com.example.demo.publisher;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DemoRedisMessagePublisher {

	private static final String CHANNEL_NAME = "REDIS-CHANNELs";

	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public DemoRedisMessagePublisher(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void publish(DemoMyMessage message) throws Exception {
		String json = objectMapper.writeValueAsString(message);
		redisTemplate.convertAndSend(CHANNEL_NAME, json);
		System.out.println("Published message to Redis: " + json);
	}
}
