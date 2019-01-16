package com.seasy.springcloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy //开启zuul功能
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
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
