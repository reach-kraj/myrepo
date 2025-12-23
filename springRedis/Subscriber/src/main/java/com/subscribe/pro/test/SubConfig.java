package com.subscribe.pro.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class SubConfig {
	String channelName = "REDIS-CHANNELs";

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
	    return new MessageListenerAdapter(new Subscriber());
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(
	        RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	    container.setConnectionFactory(redisConnectionFactory);
	    container.addMessageListener(messageListenerAdapter, new ChannelTopic(channelName));
	    return container;
	}
}
