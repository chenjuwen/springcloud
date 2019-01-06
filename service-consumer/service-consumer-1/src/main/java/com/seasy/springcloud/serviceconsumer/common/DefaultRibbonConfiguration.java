package com.seasy.springcloud.serviceconsumer.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
 * 自定义Ribbon配置类
 * 
 * 该配置类不能被@ComponentScan扫描到，否则配置信息就会被所有 @RibbonClient 共享
 * Ribbon默认配置类是 RibbonClientConfiguration
 */
//@Configuration
public class DefaultRibbonConfiguration {
//	@Bean
	public IRule ribbonRule(){
		IRule rule = new RandomRule(); //随机
	    return rule;
	}
}
