package com.seasy.springcloud.gateway.test;

import com.seasy.springcloud.gateway.common.Response;

import net.sf.json.JSONObject;

public class JsonTest {
	public static void main(String[] args) {
		System.out.println(JSONObject.fromObject(new Response(0, "success")).toString());
	}
}
