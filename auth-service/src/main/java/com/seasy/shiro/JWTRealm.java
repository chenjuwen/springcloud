package com.seasy.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.seasy.mybatis.entity.UserEntity;
import com.seasy.service.UserService;
import com.seasy.utils.JWTUtil;
import com.seasy.utils.StringUtil;

/**
 * 安全数据源：从Realm获取安全数据（如用户、角色、权限）用于验证用户身份的合法性
 */
public class JWTRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}
	
	/**
	 * 认证：获取身份验证信息，并进行登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		System.out.println("2. doGetAuthenticationInfo ...");
		String token = (String)authcToken.getPrincipal();
		
		String username = JWTUtil.getUsername(token);
		if(StringUtil.isEmpty(username)){
			throw new AuthenticationException("token invalid");
		}
		
		UserEntity userEntity = userService.getUserByLoginName(username);
		if (userEntity == null) {
            throw new AuthenticationException("user not exists");
        }
		
		if(!JWTUtil.verify(token, username)){
			throw new AuthenticationException("token verify error");
		}
		
		return new SimpleAuthenticationInfo(token, token, "my_realm");
	}

	/**
	 * 授权：设置授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo ...");
		String username = JWTUtil.getUsername(principals.toString());
		UserEntity userEntity = userService.getUserByLoginName(username);
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(userEntity.getRoleList());
		simpleAuthorizationInfo.addStringPermissions(userEntity.getRoleList());
		return simpleAuthorizationInfo;
	}

}
