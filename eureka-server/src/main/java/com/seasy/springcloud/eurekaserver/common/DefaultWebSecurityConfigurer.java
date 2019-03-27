package com.seasy.springcloud.eurekaserver.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Finchey版本中，security默认启用了csrf检验，如果不关闭该检验，eureka client端向eureka server注册时，
 * 会报如下异常：com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server
 */
@EnableWebSecurity
public class DefaultWebSecurityConfigurer extends WebSecurityConfigurerAdapter{
	private static Logger logger = LoggerFactory.getLogger(DefaultWebSecurityConfigurer.class);
	private boolean csrfEnable = false;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("csrfEnable=" + csrfEnable);
		if(csrfEnable){
			enableCSRF(http);
		}else{
			disableCSRF(http);
		}
	}
	
	private void enableCSRF(HttpSecurity http) throws Exception {
		//Spring Security默认开启了所有 CSRF 攻击防御，需要禁用 /eureka 的防御
		http.csrf().ignoringAntMatchers("/eureka/**");
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}
	
	private void disableCSRF(HttpSecurity http) throws Exception {
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		
		//关闭csrf
		http.csrf().disable(); 
		
		//开启认证：URL格式登录必须是httpBasic
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}
	
}
