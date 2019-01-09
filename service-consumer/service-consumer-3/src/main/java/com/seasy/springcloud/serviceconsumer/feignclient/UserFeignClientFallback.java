package com.seasy.springcloud.serviceconsumer.feignclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

/**
 * 回退类：需要实现Feign Client接口类
 */
@Component
public class UserFeignClientFallback implements UserFeignClient{
	@Override
	public String addUser(Long id) {
		return "error";
	}

	@Override
	public String query(String name) {
		return "error";
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
		return "error";
	}

	@Override
	public List<Address> getAllAddress() {
		return new ArrayList<Address>();
	}

	@Override
	public void put(Address address) {
		System.out.println("put >> error");
	}
	
}
