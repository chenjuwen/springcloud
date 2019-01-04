package com.seasy.springcloud.serviceprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping("/{id}")
	public String addUser(@PathVariable(value="id") Long id){
		return "provider >> id=" + id;
	}

	@GetMapping("/query")
	public String query(@RequestParam(value="name") String name){
		return "provider >> name=" + name;
	}
	
}
