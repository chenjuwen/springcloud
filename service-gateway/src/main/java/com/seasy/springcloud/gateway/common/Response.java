package com.seasy.springcloud.gateway.common;

public class Response {
	private String code;
	private String message;
	private String data;
	
	public Response(){
		
	}
	
	public Response(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	public Response(String code, String message, String data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
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
