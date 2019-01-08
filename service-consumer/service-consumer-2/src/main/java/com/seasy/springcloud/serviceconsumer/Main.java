package com.seasy.springcloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.seasy.springcloud.serviceapi.common.ExcludeComponent;

@EnableEurekaClient
@EnableFeignClients //开启Feign功能
//使用自定义注解@ExcludeComponent和excludeFilters使RibbonConfiguration类不被@CompantScan扫描到
@ComponentScan(excludeFilters=@ComponentScan.Filter(type=FilterType.ANNOTATION, value={ExcludeComponent.class}))
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
