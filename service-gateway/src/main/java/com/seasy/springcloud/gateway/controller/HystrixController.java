package com.seasy.springcloud.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.springcloud.gateway.common.Response;

/**
 * 熔断器控制类
 */
@RestController
public class HystrixController {
	@GetMapping("/hystrixCommandFallback")
	public Response hystrixCommandFallback(){
		Response response = new Response(100, "服务暂时不可用");
		return response;
	}
}
