package com.seasy.springcloud.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;
import com.seasy.springcloud.serviceconsumer.remote.ServiceRemote;

//@RestController
public class UserController {
	@Autowired
    ServiceRemote serviceRemote;
	
	@GetMapping("/consumer")
	public String test(){
		String result = serviceRemote.addUser(22L);
		System.out.println("result=" + result);

		result = serviceRemote.query("cjm");
		System.out.println("result=" + result);
		
		Address address = new Address();
		address.setProvince("GuangDong");
		address.setCity("GuangZhou");
		
		User user = serviceRemote.add(address);
		System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getPassword());
		
		return "ok";
	}
	
}
