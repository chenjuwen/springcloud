package com.seasy.shiro.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seasy.mybatis.entity.RolesEntity;
import com.seasy.service.UserService;
import com.seasy.shiro.SeasyUsernamePasswordToken;
import com.seasy.shiro.SecurityConstants;
import com.seasy.shiro.SecurityUser;
import com.seasy.shiro.ShiroConfig;
import com.seasy.utils.StringUtil;

public class SeasyFormAuthenticationFilter extends FormAuthenticationFilter {
	private static Logger logger = LoggerFactory.getLogger(SeasyFormAuthenticationFilter.class);
	private UserService userService;
	
	public String getCaptchaParam() {
		return "captcha";
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		System.out.println("1. create AuthenticationToken ...");
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		String host = getHost(request);
		
		if (StringUtil.isEmpty(password)) {
			char[] passwordArray = new char[0];
			return new SeasyUsernamePasswordToken(username, passwordArray, true, host, captcha);
		} else {
			return new SeasyUsernamePasswordToken(username, password.toCharArray(), true, host, captcha);
		}
	}

	/**
	 * 登录成功，从数据库加载资源、权限等信息
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest req,
			ServletResponse resp) throws Exception {
		System.out.println("3. onLoginSuccess ...");
		HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        
        //reset
        SecurityUser securityUser = (SecurityUser)subject.getPrincipal();
        securityUser.getRoleNoList().clear();
        
        // 用户拥有的角色资源
		List<RolesEntity> roleList = userService.getAllRoleByUserId(securityUser.getId());
		for(RolesEntity dto: roleList){
			if(StringUtil.isNotEmpty(dto.getRoleNo())){
				securityUser.getRoleNoList().add(dto.getRoleNo());
			}
		}
		
		// 跳转到主页
        request.getRequestDispatcher(ShiroConfig.LOGIN_SUCCESS_URL).forward(request, response);
        
        //false表示不继续执行filter链了
        return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		try{
			System.out.println("onLoginFailure ...");
	        HttpServletRequest req = (HttpServletRequest)request;
	        String errorMessage = (String)req.getSession().getAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG);
	        
	        if(StringUtil.isEmpty(errorMessage)){
				if(e instanceof CredentialsException){
			        req.getSession().setAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_USERNAME_PASSWORD);
				}
	        }
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("onLoginFailure", ex);
		}
		
		return super.onLoginFailure(token, e, request, response);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}