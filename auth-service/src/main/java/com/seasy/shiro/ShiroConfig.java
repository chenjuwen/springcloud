package com.seasy.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.seasy.common.SpringContextHolder;
import com.seasy.service.UserService;
import com.seasy.shiro.filter.SeasyFormAuthenticationFilter;
import com.seasy.shiro.filter.SeasyRoleAuthorizationFilter;

@Configuration
public class ShiroConfig {
	private static final String LOGIN_URL = "/login";
	public static final String LOGIN_SUCCESS_URL = "/index";
	public static final String PAGE_403 = "/common/403.jsp";
	
	@Bean  
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("shiroDelegatingFilter");
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.addUrlPatterns("/*");
        return bean;  
    }

	@Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(ApplicationContext context) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setLoginUrl(LOGIN_URL);
        bean.setSuccessUrl(LOGIN_SUCCESS_URL);
        bean.setUnauthorizedUrl(PAGE_403);
        
        bean.setSecurityManager(getSecurityManager());
        
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", seasyFormAuthenticationFilter(context));
        filters.put("anyRole", seasyRoleAuthenticationFilter());
        filters.put("logout", seasyLogoutFilter());
        bean.setFilters(filters);
        
        //拦截器
        Map<String,String> mapping = new LinkedHashMap<String,String>();
        mapping.put(LOGIN_URL, "authc");
        mapping.put("/js/**", "anon");
        mapping.put("/common/**", "anon");
        mapping.put("/kaptcha/**", "anon");
        mapping.put("/getUser", "anyRole[admin,user]");
        mapping.put("/logout*", "logout");
        mapping.put("/**", "authc");
        
        bean.setFilterChainDefinitionMap(mapping);
        return bean;
    }

	@Bean
	public SpringContextHolder getSpringContextHolder(){
		return new SpringContextHolder();
	}
    
    /**
     * 安全事务管理器
     */
    @Bean
    public SecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setCacheManager(getEhCacheManager());
        securityManager.setRealm(getAuthorizingRealm());
        securityManager.setSessionManager(getSessionManager());
        return securityManager;
    }
	
	/**
	 * 缓存管理器
	 */
	@Bean(name="ehCacheManager")
    public EhCacheManager getEhCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();  
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");  
        return ehCacheManager;  
    }

	/**
	 * 权限登录器
	 */
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")  
    public SeasyAuthorizingRealm getAuthorizingRealm(){
    	SeasyAuthorizingRealm authorizingRealm = new SeasyAuthorizingRealm();
        return authorizingRealm;
    }
    
    @Bean  
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {  
        return new LifecycleBeanPostProcessor();  
    }
	
	@Bean(name="sessionManager")
    public DefaultWebSessionManager getSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(getEhCacheManager());
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
    
    public SeasyFormAuthenticationFilter seasyFormAuthenticationFilter(ApplicationContext context){
    	SeasyFormAuthenticationFilter filter = new SeasyFormAuthenticationFilter();
    	filter.setUserService(context.getBean(UserService.class));
    	return filter;
    }
    
    public SeasyRoleAuthorizationFilter seasyRoleAuthenticationFilter(){
    	SeasyRoleAuthorizationFilter filter = new SeasyRoleAuthorizationFilter();
    	filter.setUnauthorizedUrl(PAGE_403);
    	return filter;
    }
    
    public LogoutFilter seasyLogoutFilter(){
    	LogoutFilter filter = new LogoutFilter();
    	filter.setRedirectUrl(LOGIN_URL);
    	return filter;
    }
    
}
