package com.seasy.springcloud.configconsumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //开启配置中心的更新机制。接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class UserController {
	//此参数值来自于配置中心
	@Value("${jdbc.username}")
    private String username;
	
	@GetMapping("/user/info")
    public String getUsername() {
        return username;
    }
}
