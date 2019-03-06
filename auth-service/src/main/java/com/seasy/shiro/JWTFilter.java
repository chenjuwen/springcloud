package com.seasy.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seasy.utils.StringUtil;

public class JWTFilter extends BasicHttpAuthenticationFilter {
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("preHandle...");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        //支持跨域
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        
        return super.preHandle(request, response);
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		System.out.println("isAccessAllowed...");
		if(isLoginAttempt(request, response)){
			try{
				executeLogin(request, response);
			}catch(Exception ex){
				response401(request, response);
			}
		}
		return true;
	}
	
	/**
	 * 判断用户是否想要登入
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		System.out.println("isLoginAttempt...");
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");
		System.out.println("authorization=" + authorization);
        return StringUtil.isNotEmpty(authorization);
	}
	
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("executeLogin...");
		HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        
        //提交给realm进行登入，如果错误会抛出异常并被捕获
        getSubject(request, response).login(token);
        
        //没有抛出异常则代表登入成功，返回true
        return true;
	}
	
	private void response401(ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect("/401");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
}
