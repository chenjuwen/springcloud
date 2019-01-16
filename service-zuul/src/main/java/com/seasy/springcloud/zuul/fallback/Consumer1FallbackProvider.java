package com.seasy.springcloud.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * Zuul路由熔断
 * 		Zuul目前只支持服务级别的熔断，不支持具体到某个URL进行熔断。
 */
@Component
public class Consumer1FallbackProvider implements FallbackProvider{
//	private String serviceName = "service-consumer-1";
	private String serviceName = "*";
	
	/**
	 * 表明是为哪个微服务提供回退，*表示为所有微服务提供回退
	 */
	@Override
	public String getRoute() {
		return serviceName;
	}
	
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		System.out.println(route + ": " + cause.getMessage());
		return new DefaultClientHttpResponse(route, cause);
	}
	
}
