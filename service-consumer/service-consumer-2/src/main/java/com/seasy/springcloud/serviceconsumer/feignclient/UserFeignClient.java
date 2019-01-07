package com.seasy.springcloud.serviceconsumer.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import com.seasy.springcloud.serviceapi.common.DefaultFeignConfiguration;
import com.seasy.springcloud.serviceapi.service.UserService;

/**
 * Feign客户端接口类
 */
@FeignClient(name="service-provider-1", configuration=DefaultFeignConfiguration.class) //服务名
public interface UserFeignClient extends UserService{
	
}
