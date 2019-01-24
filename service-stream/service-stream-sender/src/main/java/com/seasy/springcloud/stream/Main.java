package com.seasy.springcloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Source.class) //启动与消息中间件的绑定
public class Main {
	/**
	 * 消息channel
	 */
	@Autowired
	private Source source;
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@GetMapping("/index")
	public String index(){
		//将消息通过channel发送到目的地
		Message<String> message = MessageBuilder.withPayload("Message from sender").build();
		source.output().send(message);
		
		return "service stream sender";
	}
	
}
