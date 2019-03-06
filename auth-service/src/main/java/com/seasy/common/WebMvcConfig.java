package com.seasy.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	/**
	 * 注册验证码Servlet
	 */
	@Bean   
    public ServletRegistrationBean servletRegistrationBean() throws ServletException{  
		List<String> urlMappings = new ArrayList<String>();
		urlMappings.add("/kaptcha/kaptcha.jpg");
		
		ServletRegistrationBean servlet = new ServletRegistrationBean();
		servlet.setServlet(new KaptchaServlet());
		servlet.setUrlMappings(urlMappings);
		
		servlet.addInitParameter("kaptcha.border", "no"/*kborder*/);//无边框  
        servlet.addInitParameter("kaptcha.session.key", "KAPTCHA_SESSION_KEY");//session key  
        servlet.addInitParameter("kaptcha.textproducer.font.color", "black");  
        servlet.addInitParameter("kaptcha.textproducer.font.size", "35");  
        servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");  
        servlet.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");  //去掉干扰线
        servlet.addInitParameter("kaptcha.image.width", "100");  
        servlet.addInitParameter("kaptcha.image.height", "45");  
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");  
        servlet.addInitParameter("kaptcha.textproducer.char.space", "5");  
        servlet.addInitParameter("kaptcha.background.clear.from", "247,247,247"); //和登录框背景颜色一致   
        servlet.addInitParameter("kaptcha.background.clear.to", "247,247,247");
		
		return servlet;
    }
	
}
