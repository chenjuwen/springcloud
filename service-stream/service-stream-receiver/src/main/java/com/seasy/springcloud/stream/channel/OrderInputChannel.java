package com.seasy.springcloud.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义消息通道
 */
public interface OrderInputChannel {
	String INPUT = "input";
	
	@Input(OrderInputChannel.INPUT)
	SubscribableChannel input();
	
}
