package com.seasy.springcloud.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

public class DefaultClientHttpResponse implements ClientHttpResponse {
	private String serviceName = "";
	
	public DefaultClientHttpResponse(String serviceName){
		this.serviceName = serviceName;
	}
	
	@Override
	public InputStream getBody() throws IOException {
		//响应体
		return new ByteArrayInputStream((getServiceName() + " 微服务不可用，请稍后再试").getBytes());
	}

	@Override
	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(mediaType);
		return headers;
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		//fallback时的状态码
        return HttpStatus.OK;
	}

	@Override
	public int getRawStatusCode() throws IOException {
		//数字类型的状态码
        return this.getStatusCode().value();
	}

	@Override
	public String getStatusText() throws IOException {
		//状态文本
		return this.getStatusCode().getReasonPhrase();
	}

	@Override
	public void close() {
		
	}

	public String getServiceName() {
		return serviceName;
	}

}
