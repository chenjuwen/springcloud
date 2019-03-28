package com.seasy.springcloud.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine //开启Turbine
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
