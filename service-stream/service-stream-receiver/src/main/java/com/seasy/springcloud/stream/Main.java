package com.seasy.springcloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订阅、接收消息
 */
@SpringBootApplication
@RestController
@EnableBinding({Sink.class}) //启动与消息中间件的绑定
public class Main {
	private String message = "";
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@GetMapping("/index")
	public String index(){
		return "service stream receiver: " + message;
	}
	
	/**
	 * 监听指定Channel，通过该Channel从目的地获取消息
	 */
	@StreamListener(Sink.INPUT)
    public void receiveFromSink(String payload) {
		message = payload;
        System.out.println("Received1: " + payload);
    }
}
