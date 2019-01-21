package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 请求执行时间计算
 */
public class ExecutionTimeFilter implements GatewayFilter, Ordered {
	private static final String START_TIME = "startTime";
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
		
		return chain.filter(exchange).then(
			Mono.fromRunnable(() -> {
				Long startTime = exchange.getAttribute(START_TIME);
				Long endTime = (System.currentTimeMillis() - startTime);
				if (startTime != null) {
                    System.out.println(exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
                }
			})
		);
	}
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
