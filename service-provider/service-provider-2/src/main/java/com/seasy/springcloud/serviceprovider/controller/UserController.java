package com.seasy.springcloud.serviceprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping("/{id}")
	public String addUser(@PathVariable(value="id") Long id){
		return "provider2 >> id=" + id;
	}

	@GetMapping("/query")
	public String query(@RequestParam(value="name") String name){
		return "provider >> name=" + name;
	}
	
	@PostMapping("/add")
	public User add(@RequestBody Address address){
		System.out.println(address.getProvince() + ", " + address.getCity());
		User user = new User();
		user.setId(100L);
		user.setUsername("uid");
		user.setPassword("pwd");
		return user;
	}
	
}
