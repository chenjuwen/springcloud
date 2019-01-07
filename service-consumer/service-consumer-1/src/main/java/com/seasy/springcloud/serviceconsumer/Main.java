package com.seasy.springcloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import com.seasy.springcloud.serviceapi.common.DefaultRibbonConfiguration;
import com.seasy.springcloud.serviceapi.common.ExcludeComponent;

@EnableEurekaClient //注册中心是eureka时用此注解
//@EnableDiscoveryClient  //注册中心是非eureka时用此注解

//使用 @RibbonClient 或 @RibbonClients 注解为服务提供者指定配置类
@RibbonClient(name="service-provider-1", configuration=DefaultRibbonConfiguration.class)

//使用自定义注解@ExcludeComponent和excludeFilters使RibbonConfiguration类不被@CompantScan扫描到
@ComponentScan(excludeFilters=@ComponentScan.Filter(type=FilterType.ANNOTATION, value={ExcludeComponent.class}))
@SpringBootApplication
public class Main {
	@Bean
	@LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
