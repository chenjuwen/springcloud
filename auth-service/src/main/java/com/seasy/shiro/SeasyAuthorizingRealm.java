package com.seasy.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Constants;
import com.seasy.mybatis.entity.UsersEntity;
import com.seasy.service.UserService;
import com.seasy.utils.StringUtil;

/**
 * 安全数据源：从Realm获取安全数据（如用户、角色、权限）用于验证用户身份的合法性
 */
public class SeasyAuthorizingRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(SeasyAuthorizingRealm.class);
	
	@Autowired
	private UserService userService;

	public void setSessionAttribute(String key, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}
	
	public void removeSessionAttribute(String key){
		SecurityUtils.getSubject().getSession().removeAttribute(key);
	}
	
	/**
	 * CredentialsMatcher：密码匹配
	 * 设定Password校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(userService.getEncryptionPolicy().getCredentialsMatcher());
	}
	
	/**
	 * 认证：获取身份验证信息，并进行登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		SeasyUsernamePasswordToken token = null;
		try {
			System.out.println("2. doGetAuthenticationInfo ...");
			removeSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG);
			
			token = (SeasyUsernamePasswordToken) authcToken;
			
			//用户名和密码的判断
			if (StringUtil.isEmpty(token.getUsername()) || null == token.getPassword()
					|| 0 == token.getPassword().length) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_USERNAME_PASSWORD);
				return null;
			}
			
			//验证码的判断
			if (StringUtil.isEmpty(token.getCaptcha())) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_CAPTCHA);
				return null;
			}

			token.setCaptcha(token.getCaptcha().toLowerCase());

			//验证码是否正确
			String captchaId = (String)SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			
			//判断用户输入的验证码是否正确
			if (!token.getCaptcha().equalsIgnoreCase(captchaId)) {
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_CAPTCHA);
				return null;
			}

			//验证用户名是否存在
			UsersEntity user = userService.getUserByLoginName(token.getUsername());
			if(user == null){
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_USERNAME_PASSWORD);
				return null;
			}
			
			//状态是否禁用
			if(1 != user.getEnabled()){
				setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, SecurityConstants.ERROR_STATUS);
				return null;
			}
			
			SecurityUser securityUser = new SecurityUser(user.getId(), user.getLoginName(), token.getCaptcha());
			securityUser.setPassword(new String(token.getPassword()));

			token.setShiroUser(securityUser);

			byte[] salt = Hex.decodeHex(user.getSalt().toCharArray());
			return new SimpleAuthenticationInfo(securityUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());

		} catch (Exception e) {
			logger.error("doGetAuthenticationInfo error", e);
			setSessionAttribute(SecurityConstants.SESSION_ATTR_KEY__ERRORMSG, "登录失败: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 授权：设置授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		try {
			System.out.println("doGetAuthorizationInfo ...");
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			
			SecurityUser securityUser = (SecurityUser)principals.getPrimaryPrincipal();
			
			//用户的角色权限
			List<String> roleList = securityUser.getRoleNoList();
			for(String roleNo: roleList){
				info.addRole(roleNo);
			}
			
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
