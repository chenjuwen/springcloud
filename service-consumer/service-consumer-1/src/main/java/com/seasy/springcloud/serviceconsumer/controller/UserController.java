package com.seasy.springcloud.serviceconsumer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

@RestController
public class UserController {
	@Autowired
    private RestTemplate restTemplate;
	
	@GetMapping("/user/{id}")
	public String addUser(@PathVariable Long id){
		//服务提供者的接口地址：此处用应用名
		String baseURL = "http://service-provider-1" ;
		
		String result = restTemplate.getForObject(baseURL + "/user/{id}", String.class, id);
		System.out.println(result);
		
		result = restTemplate.getForObject(baseURL + "/user/query?name={1}", String.class, "中文");
		System.out.println(result);
		
		User user = restTemplate.getForObject(baseURL + "/user/getUser?id={1}", User.class, id);
		System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getPassword());
		
		Address address = new Address();
		address.setProvince("广东");
		address.setCity("广州");
		
		user = restTemplate.postForObject(baseURL + "/user/add", address, User.class, new Object());
		System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getPassword());
		
		result = restTemplate.postForObject(baseURL + "/user/update", address, String.class, new Object());
		System.out.println(result);
		
		ResponseEntity<List> listEntity = restTemplate.getForEntity(baseURL + "/user/getAllAddress", List.class, new Object());
		List list = listEntity.getBody();
		for(Object obj : list){
			Map map = (Map)obj;
			System.out.println(map.get("province") + ", " + map.get("city"));
		}
		
		//没有返回值
		ResponseEntity<Void> voidEntity = restTemplate.postForEntity(baseURL + "/user/put", address, Void.class, new Object());
		System.out.println(voidEntity.getStatusCodeValue());
		
		//restTemplate.delete(url, uriVariables);
		//restTemplate.exchange(requestEntity, responseType)
		//restTemplate.exchange(url, method, requestEntity, responseType, uriVariables)
		//restTemplate.execute(url, method, requestCallback, responseExtractor)
		//restTemplate.patchForObject(url, request, responseType)
		//restTemplate.setErrorHandler(errorHandler);
		//restTemplate.setInterceptors(interceptors);
		//restTemplate.setRequestFactory(requestFactory);
		
		return System.currentTimeMillis() + " >> consumer1 >> " + result;
	}
	
}
