package com.seasy.springcloud.gateway.filter;

import java.nio.charset.Charset;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;

import com.seasy.springcloud.gateway.common.Response;

import net.sf.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 统一处理接口的返回报文
 */
public class WrapperResponseFilter implements GlobalFilter, Ordered {
	@Override
	public int getOrder() {
		return -2;
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        
                        // 释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        
                        Response response = new Response();
                        response.setCode(0);
                        response.setMessage("请求成功");
                        response.setData(new String(content, Charset.forName("UTF-8")));
                        
                        byte[] newData = JSONObject.fromObject(response).toString().getBytes(Charset.forName("UTF-8"));
                        
                        //如果不重新设置长度则收不到消息
                        originalResponse.getHeaders().setContentLength(newData.length);
                        return bufferFactory.wrap(newData);
                    }));
                }
                
                return super.writeWith(body);
            }
        };
        
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
	}
}
