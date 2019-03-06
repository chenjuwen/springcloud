package com.seasy.encryptionpolicy;

public class EncryptionResult {
	/**
	 * 盐值
	 */
	private String salt;
	
	/**
	 * 密码
	 */
	private String password;
	
	public EncryptionResult(){
		
	}
	
	public EncryptionResult(String salt, String password){
		this.salt = salt;
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
