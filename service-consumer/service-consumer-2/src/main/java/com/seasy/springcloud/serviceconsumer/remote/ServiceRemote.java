package com.seasy.springcloud.serviceconsumer.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

/**
 * Feign的客户端接口类
 */
//@FeignClient(name="service-provider-1") //要调用的服务名称
//@RequestMapping("/user")
public interface ServiceRemote {
	//接口方法的结构要与服务提供者Controller定义的一致
	@GetMapping("/{id}")
	String addUser(@PathVariable(value="id") Long id);
	
	@GetMapping("/query")
	String query(@RequestParam(value="name") String name);
	
	@PostMapping("/add")
	User add(@RequestBody Address address);	
}
