package com.seasy.springcloud.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义消息通道
 */
public interface OrderOutputChannel {
	String OUTPUT = "output";
	
	@Output(OrderOutputChannel.OUTPUT)
	MessageChannel output();
}
