package com.seasy.springcloud.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderOutputChannel {
	String OUTPUT = "output";
	
	@Output(OrderOutputChannel.OUTPUT)
	MessageChannel output();
}
