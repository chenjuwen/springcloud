package com.seasy.springcloud.serviceprovider.controller;

import java.util.ArrayList;
import java.util.List;

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
		return "provider1 >> id=" + id;
	}

	@GetMapping("/query")
	public String query(@RequestParam(value="name") String name){
		return "provider1 >> 名字=" + name;
	}
	
	@GetMapping("/getUser")
	public User getUser(@RequestParam(value="id") Long id){
		User user = new User();
		user.setId(id);
		user.setUsername("姓名1");
		user.setPassword("pwd1");
		return user;
	}
	
	@PostMapping("/add")
	public User add(@RequestBody Address address){
		System.out.println(address.getProvince() + ", " + address.getCity());
		User user = new User();
		user.setId(100L);
		user.setUsername("姓名1");
		user.setPassword("pwd1");
		return user;
	}

	@PostMapping("/update")
	public String update(@RequestBody Address address){
		System.out.println(address.getProvince() + ", " + address.getCity());
		return "provider1 >> update success";
	}
	
	@GetMapping("/getAllAddress")
	public List<Address> getAllAddress(){
		List<Address> list = new ArrayList<Address>();
		Address address1 = new Address("省1", "city1");
		Address address2 = new Address("province11", "市11");
		list.add(address1);
		list.add(address2);
		return list;
	}

	@PostMapping("/put")
	public void put(@RequestBody Address address){
		System.out.println(address.getProvince() + ", " + address.getCity());
	}
	
}
