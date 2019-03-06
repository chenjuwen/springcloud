package com.seasy.mybatis.entity;

public class RoleEntity extends BaseEntity {
	private static final long serialVersionUID = 3881618593057766194L;

	private Long id;
	private String roleNo; 	//角色编号
	private String roleName; 	//角色名称
	private String roleDesc; 	//角色描述
	
	public RoleEntity(){
		
	}
	
	public RoleEntity(String roleNo, String roleName, String roleDesc){
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRoleNo() {
		return roleNo;
	}
	
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}
	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
