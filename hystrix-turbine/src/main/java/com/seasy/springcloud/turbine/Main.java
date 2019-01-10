package com.seasy.springcloud.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableHystrixDashboard //启用Hystrix Dashboard功能
@EnableTurbine //开启Turbine
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
