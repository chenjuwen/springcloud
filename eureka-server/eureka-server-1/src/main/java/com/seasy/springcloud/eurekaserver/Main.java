package com.seasy.springcloud.eurekaserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Eureka Server: 服务注册中心
 */
@EnableEurekaServer
@SpringBootApplication
@RestController
public class Main {
	@Value("${eureka.instance.hostname}")
	private String hostname;
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	/**
	 * 返回本机的IP的地址
	 */
	@GetMapping("/getLocalIP")
	public String test(){
		return hostname;
	}
	
}
