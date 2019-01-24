package com.seasy.springcloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.stream.bean.User;
import com.seasy.springcloud.stream.channel.OrderInputChannel;

/**
 * 订阅、接收消息
 */
@SpringBootApplication
@RestController
@EnableBinding({OrderInputChannel.class}) //启动与消息中间件的绑定
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
	@StreamListener(OrderInputChannel.INPUT)
    public void receive(String payload) {
		message = payload;
        System.out.println("Received1: " + payload);
    }
	
	/**
	 * 将json格式的消息转成User对象
	 */
	@StreamListener(OrderInputChannel.INPUT)
    public void receive2(User user) {
		System.out.println(user.getClass().getName());
        System.out.println("usernaem=" + user.getUsername() + ", password=" + user.getPassword());
    }
}
