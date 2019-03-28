package com.seasy.springcloud.gateway.ratelimiter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
	@Bean(name="remoteAddressKeyResolver")
	public RemoteAddressKeyResolver remoteAddressKeyResolver(){
		return new RemoteAddressKeyResolver();
	}
}
