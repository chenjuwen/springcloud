package com.seasy.springcloud.serviceapi.bean;

import java.io.Serializable;

public class Address implements Serializable{
	private static final long serialVersionUID = 9219836610932703975L;
	
	private String province;
	private String city;
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
}
