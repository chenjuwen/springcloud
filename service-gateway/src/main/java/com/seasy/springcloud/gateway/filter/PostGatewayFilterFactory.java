package com.seasy.springcloud.gateway.filter;

import java.util.List;

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
		System.out.println("PostGatewayFilterFactory...");
		
		return (exchange, chain) -> {
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				ServerHttpResponse response = exchange.getResponse();
				
				List<String> list = response.getHeaders().get("X-Response-Foo");
				System.out.println(list);
			}));
		};
	}
	
	public static class Config{
		
	}
}
