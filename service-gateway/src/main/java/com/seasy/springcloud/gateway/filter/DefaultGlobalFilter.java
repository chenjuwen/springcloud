package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 全局过滤器：可实现限流过滤器、全局断路器、全局鉴权过滤器等
 */
public class DefaultGlobalFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("DefaultGlobalFilter...");

		ServerHttpRequest request = exchange.getRequest();
		
		request.getURI();
		request.getRemoteAddress();
		
		//METHOD
		String method = request.getMethodValue();
		
		//获取请求变量值
		String token = request.getQueryParams().getFirst("token");
		
		//属性数据
		exchange.getAttributes().put("key", "value");
		exchange.getAttribute("key");
		exchange.getRequiredAttribute("key");
		
		//设置headers参数
		request.getHeaders().get("X-Request-Foo");
		
		ServerHttpRequest.Builder builder = request.mutate();
		builder.header("GlobalFilter","GlobalFilter success");
		ServerHttpRequest newRequest = builder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
	}
}
