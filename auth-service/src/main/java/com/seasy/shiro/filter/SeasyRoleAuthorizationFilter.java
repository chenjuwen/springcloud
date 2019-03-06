package com.seasy.shiro.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据角色判断用户是否有访问权限
 */
public class SeasyRoleAuthorizationFilter extends SeasyAuthorizationFilter {
	private static Logger logger = LoggerFactory.getLogger(SeasyRoleAuthorizationFilter.class);
	
	/**
	 * 是否允许访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		
		//角色权限判断 
		boolean hasRoleAuthority = false;
	    String[] rolesArray = (String[])mappedValue;
	    if(rolesArray == null || rolesArray.length == 0){
	    	hasRoleAuthority = true;
	    }else{
			logger.debug("mappedValue: " + Arrays.toString(rolesArray));
	    	Set<String> roles = CollectionUtils.asSet(rolesArray);
		    for(String role : roles){
	            if(subject.hasRole(role)){  
	            	hasRoleAuthority = true;
	            	break;
	            }  
	        } 
	    }
	    
	    //返回true表示允许访问，返回false表示不允许访问
	    if(hasRoleAuthority){
	    	return true;
	    }else{
	    	return false;
	    }
	}

	/**
	 * 访问拒绝时触发该方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		if(getUnauthorizedUrl() != null){
			WebUtils.issueRedirect(request, response, getUnauthorizedUrl());
		}else{
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
		//返回false表示该拦截器已处理，直接返回即可。返回true表示需要继续处理
		return false; 
	}

}
