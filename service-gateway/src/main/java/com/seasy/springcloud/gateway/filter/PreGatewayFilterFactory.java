package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {
	public PreGatewayFilterFactory(){
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		System.out.println("PreGatewayFilterFactory...");
		
		return (exchange, chain) -> {
			//在chain.filter之前的代码为pre的生命周期
			ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
			builder.header("GatewayFilter", "PreGatewayFilterFactory success");
			ServerHttpRequest newRequest = builder.build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
			System.out.println("pre...");
	        return chain.filter(newExchange);
		};
	}
	
	public static class Config{
		
	}
}
