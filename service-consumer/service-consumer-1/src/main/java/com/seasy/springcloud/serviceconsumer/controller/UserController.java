package com.seasy.springcloud.serviceconsumer.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
public class UserController {
	@Autowired
    private LoadBalancerClient client;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@GetMapping("/user/{id}")
	public String addUser(@PathVariable Long id){
		System.out.println("consumer id = " + id);
		
		//通过负载均衡选出一个服务提供者的服务实例
		ServiceInstance instance = client.choose("service-provider-1");
		System.out.println(instance.getHost() + ", " + instance.getPort() + ", " + instance.getScheme() + ", " + instance.getServiceId() + ", " + instance.isSecure() + ", " + instance.getUri().toString());
		
		for(Iterator<String> it=instance.getMetadata().keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = instance.getMetadata().get(key);
			System.out.println(key + "=" + value);
		}
		
		//拼接服务调用者的接口地址：此处用IP:端口
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id;
		
		//通过RestTemplate对象调用接口
		String result = restTemplate.getForObject(url, String.class);
		
		return "consumer >> " + result;
	}
	
}
