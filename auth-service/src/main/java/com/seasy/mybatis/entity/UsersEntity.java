package com.seasy.mybatis.entity;

public class UsersEntity extends BaseEntity {
	private static final long serialVersionUID = 845288956479462514L;

	private Long id;
	private String loginName; 	//登录账号
	private String password; 	//密码
	private String salt; 	//密码盐值
	private Integer enabled; 	//状态:0禁用,1启用
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Integer getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
