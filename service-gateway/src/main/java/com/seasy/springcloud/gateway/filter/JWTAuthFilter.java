package com.seasy.springcloud.gateway.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.seasy.springcloud.gateway.common.Response;
import com.seasy.springcloud.gateway.utils.JWTUtil;
import com.seasy.springcloud.gateway.utils.StringUtil;

import net.sf.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthFilter implements GlobalFilter, Ordered{
    @Override
    public int getOrder() {
    	return -100;
    }
    
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String url = exchange.getRequest().getURI().getPath();
		System.out.println(url);
		
		//忽略以下url请求
		if(url.indexOf("/auth-service/") >= 0){ //认证
			return chain.filter(exchange);
		}
		
		//从请求头中取得token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(StringUtil.isEmpty(token)){
        	ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            
            Response res = new Response(401, "401 unauthorized");
            byte[] responseByte = JSONObject.fromObject(res).toString().getBytes(StandardCharsets.UTF_8);
            
            DataBuffer buffer = response.bufferFactory().wrap(responseByte);
            return response.writeWith(Flux.just(buffer));
        }
        
        //请求中的token是否在redis中存在
        boolean verifyResult = JWTUtil.verify(token);
        if(!verifyResult){
        	ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

            Response res = new Response(1004, "invalid token");
            byte[] responseByte = JSONObject.fromObject(res).toString().getBytes(StandardCharsets.UTF_8);
            
            DataBuffer buffer = response.bufferFactory().wrap(responseByte);
            return response.writeWith(Flux.just(buffer));
        }
        
        return chain.filter(exchange);
	}
	
}
