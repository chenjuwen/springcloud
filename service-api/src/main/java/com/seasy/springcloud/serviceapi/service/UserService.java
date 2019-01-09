package com.seasy.springcloud.serviceapi.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

public interface UserService {
	@GetMapping("/user/{id}")
	String addUser(@PathVariable(value="id") Long id);
	
	@GetMapping("/user/query")
	String query(@RequestParam(value="name") String name);
	
	@GetMapping("/user/getUser")
	User getUser(@RequestParam(value="id") Long id);
	
	@PostMapping("/user/add")
	User add(@RequestBody Address address);	
	
	@PostMapping("/user/update")
	String update(@RequestBody Address address);
	
	@GetMapping("/user/getAllAddress")
	List<Address> getAllAddress();
	
	@PostMapping("/user/put")
	void put(@RequestBody Address address);
	
}
