package com.seasy.springcloud.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.serviceconsumer.remote.ServiceRemote;

@RestController
public class User3Controller {
	@Autowired
    ServiceRemote serviceRemote;
	
	@GetMapping("/consumer")
	public String test(){
		String result = serviceRemote.addUser(22L);
		System.out.println("result=" + result);

		result = serviceRemote.query("cjm");
		System.out.println("result=" + result);
		
		return "ok";
	}
	
}
