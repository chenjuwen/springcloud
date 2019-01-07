package com.seasy.springcloud.serviceapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

@RequestMapping("/user")
public interface UserService {
	@GetMapping("/{id}")
	String addUser(@PathVariable(value="id") Long id);
	
	@GetMapping("/query")
	String query(@RequestParam(value="name") String name);
	
	@GetMapping("/getUser")
	User getUser(@RequestParam(value="id") Long id);
	
	@PostMapping("/add")
	User add(@RequestBody Address address);	
	
	@PostMapping("/update")
	String update(@RequestBody Address address);
	
	@GetMapping("/getAllAddress")
	List<Address> getAllAddress();
	
	@PostMapping("/put")
	void put(@RequestBody Address address);
	
}
