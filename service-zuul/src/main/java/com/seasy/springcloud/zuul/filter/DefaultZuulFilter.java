package com.seasy.springcloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 自定义Filter
 */
//@Component
public class DefaultZuulFilter extends ZuulFilter {
	/**
	 * 过滤器类型，它决定过滤器在请求的哪个生命周期中执行。
	 * 		pre：路由之前
	 * 		route：路由之时
	 * 		post： 路由之后
	 * 		error：处理请求发生错误时调用
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}
	
	/**
	 * filter执行顺序
	 * 数字越大，优先级越低
	 */
	@Override
	public int filterOrder() {
		return 0;
	}
	
	/**
	 * 判断该过滤器是否需要被执行。
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	/**
	 * 过滤器的具体逻辑：可以查询数据库判断是否有访问权限
	 */
	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            requestContext.setSendZuulResponse(false); //过滤该请求，不对其进行路由
            requestContext.setResponseStatusCode(401); //返回的错误码
            requestContext.setResponseBody("token is empty!!!");
            requestContext.set("isSuccess", false);
//            try {
//                requestContext.getResponse().getWriter().write("token is empty");
//            }catch (Exception ex){
//            	ex.printStackTrace();
//            }
            
            return null;
        }
        
        return null;
	}
	
}
