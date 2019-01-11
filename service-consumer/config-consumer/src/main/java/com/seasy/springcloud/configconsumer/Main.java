package com.seasy.springcloud.configconsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RefreshScope //开启配置中心的更新机制。接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class Main {
	@Value("${jdbc.username}")
    private String username;
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@GetMapping("/info")
    public String getUsername() {
        return username;
    }
	
}
