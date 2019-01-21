package com.seasy.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import reactor.core.publisher.Mono;

public class Custom1GatewayFilterFactory extends AbstractGatewayFilterFactory<Custom1GatewayFilterFactory.Config> {
	public Custom1GatewayFilterFactory(){
		//需要将Config类传递到父类
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			//pre生命周期：在chain.filter之前的代码
			System.out.println("message=" + config.getMessage());
			exchange.getAttributes().put("startTime", System.currentTimeMillis());
			
			//post生命周期：在then里面的代码
			return chain.filter(exchange).then(
				Mono.fromRunnable(() -> {
					Long startTime = exchange.getAttribute("startTime");
					if(startTime != null){
						System.out.println(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime));
					}
				})
			);
		};
	}
	
	public static class Config{
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	
}
