package com.seasy.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SecurityUser implements Serializable {
	private static final long serialVersionUID = 4855568477109664774L;
	
	private Long id;
	private String loginName; 	//登录账号
	private String password; 	//密码
	private String captcha;	//验证码
	private Long enabled; 	//状态:0禁用,1启用
	private String salt; 	//密码盐值
	
	private List<String> roleNoList = new ArrayList<String>(); //当前用户有哪些角色

	public SecurityUser(){
		
	}

	public SecurityUser(Long id, String loginName, String captcha) {
		this.id = id;
		this.loginName = loginName;
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return "ShiroUser [id=" + id + ", loginName=" + loginName + ", captcha=" + captcha + "]";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "loginName");
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "loginName");
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getEnabled() {
		return enabled;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}

	public List<String> getRoleNoList() {
		return roleNoList;
	}

	public void setRoleNoList(List<String> roleNoList) {
		this.roleNoList = roleNoList;
	}
	
}
