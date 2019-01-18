package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;

import reactor.core.publisher.Mono;

public class PostGatewayFilterFactory extends AbstractGatewayFilterFactory<PostGatewayFilterFactory.Config> {
	public PostGatewayFilterFactory() {
        super(Config.class);
    }
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			System.out.println("PostGatewayFilter...");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				ServerHttpResponse response = exchange.getResponse();
			}));
		};
	}
	
	public static class Config{
		
	}
}
