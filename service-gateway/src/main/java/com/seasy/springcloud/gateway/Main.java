package com.seasy.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@SpringBootApplication
@EnableEurekaClient
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}	
	
	/**
	 * 更改gateway服务路由的负载均衡策略
	 */
	@Bean
	public IRule feignRule(){
	    return new RandomRule();
	}
	
}
