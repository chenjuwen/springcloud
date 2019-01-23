package com.seasy.springcloud.serviceconsumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;
import com.seasy.springcloud.serviceconsumer.feignclient.UserFeignClient;

@RestController
public class UserController {
	//引入FeignClient类
	@Autowired
    UserFeignClient userFeignClient;
	
	@GetMapping("/consumer3")
	public String test(){
		//addUser
		String result = userFeignClient.addUser(22L);
		System.out.println("result=" + result);

		/*
		//query
		result = userFeignClient.query("cjm");
		System.out.println("result=" + result);
		
		//getUser
		User user = userFeignClient.getUser(33L);
		System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getPassword());
		
		//add
		Address address = new Address();
		address.setProvince("GuangDong");
		address.setCity("GuangZhou");
		
		user = userFeignClient.add(address);
		System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getPassword());
		
		//update
		result = userFeignClient.update(address);
		System.out.println("result=" + result);
		
		//getAllAddress
		List<Address> list = userFeignClient.getAllAddress();
		for(Address addr : list){
			System.out.println(addr.getProvince() + ", " + addr.getCity());
		}
		
		//put
		userFeignClient.put(address);
		*/
		
		return "ok: " + result + ", " + System.currentTimeMillis();
	}
	
}
