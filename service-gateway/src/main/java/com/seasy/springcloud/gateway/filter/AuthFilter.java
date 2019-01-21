package com.seasy.springcloud.gateway.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.seasy.springcloud.gateway.common.Response;

import net.sf.json.JSONObject;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器
 */
//@Component
public class AuthFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("AuthFilter...");
		
		String token = exchange.getRequest().getQueryParams().getFirst("token");
        if ("token".equals(token)) {
            return chain.filter(exchange);
        }
        
        //响应报文数据
        Response data = new Response("401", "not exists query param: token");
        byte[] dataArr = JSONObject.fromObject(data).toString().getBytes(StandardCharsets.UTF_8);
        
        ServerHttpResponse httpResponse = exchange.getResponse();
        DataBuffer dataBuffer = httpResponse.bufferFactory().wrap(dataArr);
        httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        httpResponse.getHeaders().add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        return httpResponse.writeWith(Mono.just(dataBuffer));
	}
}
