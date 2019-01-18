package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {
	public PreGatewayFilterFactory(){
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		System.out.println("PreGatewayFilterFactory...");
		
		return (exchange, chain) -> {
			ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
			builder.header("GatewayFilter", "PreGatewayFilterFactory success");
            return chain.filter(exchange.mutate().request(builder.build()).build());
		};
	}
	
	public static class Config{
		
	}
}
