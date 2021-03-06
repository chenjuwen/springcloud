package com.seasy.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * http://192.168.134.134:3005/hystrix
 */
@SpringBootApplication
@EnableHystrixDashboard //启用Hystrix Dashboard功能
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
