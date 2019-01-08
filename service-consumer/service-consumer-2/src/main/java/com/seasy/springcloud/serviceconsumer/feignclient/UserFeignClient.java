package com.seasy.springcloud.serviceconsumer.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import com.seasy.springcloud.serviceapi.service.UserService;

/**
 * Feign客户端接口类：通过Feign的继承特性来实现接口共享
 * 
 * name=服务名
 */
@FeignClient(name="service-provider-1", configuration=DefaultFeignConfiguration.class, fallback=UserFeignClientHystrix.class)
public interface UserFeignClient extends UserService{
	
}
