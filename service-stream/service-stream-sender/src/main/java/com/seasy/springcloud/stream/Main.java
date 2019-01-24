package com.seasy.springcloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.stream.bean.User;
import com.seasy.springcloud.stream.channel.OrderOutputChannel;

@SpringBootApplication
@RestController
@EnableBinding(OrderOutputChannel.class) //启动与消息中间件的绑定
public class Main {
	/**
	 * 消息channel
	 */
	@Autowired
	private OrderOutputChannel outputChannel;
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@GetMapping("/index")
	public String index(){
		//将消息通过channel发送到目的地
		User user = new User("cjm", "123");
		Message<User> message = MessageBuilder.withPayload(user).build();
		outputChannel.output().send(message); //Bean对象会转成json字符串存储到目的地
		
		return "service stream sender";
	}
	
}
