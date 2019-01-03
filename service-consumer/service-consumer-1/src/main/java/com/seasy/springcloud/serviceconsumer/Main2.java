package com.seasy.springcloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类：使用 Spring Cloud Ribbon方式创建消费者
 */
//@EnableDiscoveryClient
@SpringBootApplication
public class Main2 {
	public static void main(String[] args) {
		SpringApplication.run(Main2.class, args);
	}
	
	@LoadBalanced
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
