package com.seasy.springcloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类：使用 Spring Cloud Feign方式创建消费者
 */
//@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class Main3 {
	public static void main(String[] args) {
		SpringApplication.run(Main3.class, args);
	}
}
