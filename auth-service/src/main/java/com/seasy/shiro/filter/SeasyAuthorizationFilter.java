package com.seasy.shiro.filter;

import java.util.Map;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public abstract class SeasyAuthorizationFilter extends AuthorizationFilter {
	public Map<String, Object> getAppliedPaths(){
		return appliedPaths;
	}
}
