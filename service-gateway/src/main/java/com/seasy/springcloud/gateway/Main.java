package com.seasy.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.seasy.springcloud.gateway.filter.Custom1GatewayFilterFactory;

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
	
	/**
	 * 自定义过滤器
	 */
//	@Bean
//	@Order(0) //指定执行的顺序：数字越小，优先级越高
//	public AuthFilter getAuthFilter(){
//		return new AuthFilter();
//	}
//	
//	@Bean
//	@Order(1)
//	public DefaultGlobalFilter getDefaultGlobalFilter(){
//		return new DefaultGlobalFilter();
//	}
	
	@Bean
	public Custom1GatewayFilterFactory getPreGatewayFilterFactory(){
		return new Custom1GatewayFilterFactory();
	}
	
}
