package com.seasy.springcloud.gateway.common;

public class Response {
	private int code;
	private String message;
	private String data;
	
	public Response(){
		
	}
	
	public Response(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public Response(int code, String message, String data){
		this.code = code;
		this.message = message;
		this.data = data;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
