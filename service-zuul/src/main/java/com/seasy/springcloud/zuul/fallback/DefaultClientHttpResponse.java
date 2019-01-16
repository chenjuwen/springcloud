package com.seasy.springcloud.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

public class DefaultClientHttpResponse implements ClientHttpResponse {
	private String route;
	
	public DefaultClientHttpResponse(String route){
		this.route = route;
	}
	
	/**
	 * 响应体
	 */
	@Override
	public InputStream getBody() throws IOException {
		String result = "";
		try {
			JSONObject object = new JSONObject();
			object.put("code", "999");
			object.put("message", route + " 不可用!");
			result = object.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(result.getBytes());
	}

	@Override
	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
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

}
