package com.seasy.springcloud.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderChannel {
	String INPUT2 = "input2";
	
	@Input(OrderChannel.INPUT2)
	SubscribableChannel input();
	
}
