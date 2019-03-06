package com.seasy.mybatis;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {
	@Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
	
	/**
	 * 定义数据源
	 */
	@Bean(name = "druidDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource") //可以在application.properties中直接导入
	public DataSource druidDataSource(){ 
		return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
	}
	
	/**
	 * Druid监控平台
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("allow","192.168.1.218,127.0.0.1"); //白名单
		reg.addInitParameter("deny","192.168.1.100"); //黑名单，优先于allow
		reg.addInitParameter("loginUsername", "druid");
		reg.addInitParameter("loginPassword", "123456");
		return reg;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		bean.addUrlPatterns("/*");
		bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		bean.addInitParameter("profileEnable", "true");
		bean.addInitParameter("principalCookieName", "USER_COOKIE");
		bean.addInitParameter("principalSessionName", "USER_SESSION");
		return bean;
	}
	
}
