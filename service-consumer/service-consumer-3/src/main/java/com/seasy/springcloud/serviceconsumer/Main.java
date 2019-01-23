package com.seasy.springcloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
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
	
//	@Bean
//	public IRule feignRule(){
//	    return new RandomRule();
//	}
	
	/**
	 * SpringBoot2+版本需要手动配置hystrix.stream端点
	 */
	@Bean
	public ServletRegistrationBean getServlet() {
	    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
	    registrationBean.setLoadOnStartup(1);
	    registrationBean.addUrlMappings("/hystrix.stream");
	    registrationBean.setName("hystrixMetricsStreamServlet");
	    return registrationBean;
	}
	
}
