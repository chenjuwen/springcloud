package com.seasy.springcloud.serviceconsumer.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign的客户端接口类
 */
@FeignClient(name="service-provider-1") //要调用的服务名称
public interface ServiceRemote {
	//接口方法的结果要与服务提供者Controller定义的一致
	@GetMapping("/user/{id}")
	String addUser(@PathVariable(value="id") Long id);
	
	@GetMapping("/user/query")
	String query(@RequestParam(value="name") String name);
	
}
