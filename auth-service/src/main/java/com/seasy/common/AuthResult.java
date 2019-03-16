package com.seasy.common;

public class AuthResult {
	private int code;
	private String message;
	private String token;
	private String refreshToken;
	
	public AuthResult(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public AuthResult(int code, String message, String token, String refreshToken){
		this.code = code;
		this.message = message;
		this.token = token;
		this.refreshToken = refreshToken;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
