package com.seasy.adminserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer //开启监控功能
@EnableEurekaClient //Admin Server也注册到注册中心
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	/**
	 * Spring Security配置
	 */
	@Profile("insecure")  //对应到application配置文件的spring.profiles属性值
    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	//permitAll： 无条件允许访问
            http.authorizeRequests().anyRequest().permitAll()
                    .and().csrf().disable();
        }
    }
 
    @Profile("secure")
    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;
 
        public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }
 
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	//登录成功后的处理器类
            SavedRequestAwareAuthenticationSuccessHandler successHandler 
            	= new SavedRequestAwareAuthenticationSuccessHandler();
            
            successHandler.setTargetUrlParameter("redirectTo");
 
            http.authorizeRequests()
                    .antMatchers(adminContextPath + "/assets/**").permitAll()
                    .antMatchers(adminContextPath + "/login").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
                    .and().logout().logoutUrl(adminContextPath + "/logout")
                    .and().httpBasic()
                    .and().csrf().disable();
        }
    }

}
