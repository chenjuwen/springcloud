package com.seasy.springcloud.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
public class User2Controller {
	@Autowired
    private RestTemplate restTemplate;
	
	@GetMapping("/user/{id}")
	public String addUser(@PathVariable Long id){
		System.out.println("consumer id = " + id);
		
		//服务调用者的接口地址：此处用应用名
		String url = "http://service-provider-1/user/" + id;
		
		//通过RestTemplate对象调用接口
		String result = restTemplate.getForObject(url, String.class);
		
		return "consumer >> " + result;
	}
	
}
