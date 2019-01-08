package com.seasy.springcloud.serviceconsumer.feignclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

/**
 * 断路器类：服务降级，需要实现FeignClient接口类
 */
public class UserFeignClientHystrix implements UserFeignClient{
	@Override
	public String addUser(@PathVariable(value="id") Long id) {
		return "error";
	}

	@Override
	public String query(@RequestParam(value="name") String name) {
		return "error";
	}

	@Override
	public User getUser(@RequestParam(value="id") Long id) {
		return new User();
	}

	@Override
	public User add(@RequestBody Address address) {
		return new User();
	}

	@Override
	public String update(@RequestBody Address address) {
		return "error";
	}

	@Override
	public List<Address> getAllAddress() {
		return new ArrayList<Address>();
	}

	@Override
	public void put(@RequestBody Address address) {
		System.out.println("put >> error");
	}
	
}
