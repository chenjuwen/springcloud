package com.seasy.springcloud.serviceconsumer.feignclient;

import java.util.ArrayList;
import java.util.List;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;
import com.seasy.springcloud.serviceapi.service.UserService;

/**
 * 服务降级Fallback类
 */
public class UserFeignFallBack implements UserService{
	@Override
	public String addUser(Long id) {
		return "fallback";
	}

	@Override
	public String query(String name) {
		return "fallback";
	}

	@Override
	public User getUser(Long id) {
		return new User();
	}

	@Override
	public User add(Address address) {
		return new User();
	}

	@Override
	public String update(Address address) {
		return "fallback";
	}

	@Override
	public List<Address> getAllAddress() {
		return new ArrayList<Address>();
	}

	@Override
	public void put(Address address) {
		System.out.println("put >> fallback");
	}
	
}
