package com.seasy.springcloud.serviceapi.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 自定义Feign配置类
 * 
 * 该配置类不能被@ComponentScan扫描到，否则配置信息就会被所有 @FeignClient 共享
 * Feign的默认配置类是FeignClientsConfiguration，该类定义了Feign默认使用的编码器、解码器、所使用的契约等。
 */
@Configuration
@ExcludeComponent //用于标识该配置类不被@ComponentScan扫描
public class DefaultFeignConfiguration {
	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
