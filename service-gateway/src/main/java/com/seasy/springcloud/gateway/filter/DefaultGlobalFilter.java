package com.seasy.springcloud.gateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 全局过滤器：可实现限流过滤器、全局断路器、全局鉴权过滤器等
 */
@Configuration
public class DefaultGlobalFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("DefaultGlobalFilter...");
		
		ServerHttpRequest request = exchange.getRequest();
		
		List<String> list = request.getHeaders().get("X-Request-Foo");
		System.out.println(list);
		
		list = request.getQueryParams().get("foo");
		System.out.println(list);
		
		ServerHttpRequest.Builder builder = request.mutate();
		builder.header("GlobalFilter","GlobalFilter success");
		chain.filter(exchange.mutate().request(builder.build()).build());
		return chain.filter(exchange.mutate().request(builder.build()).build());
	}
}
