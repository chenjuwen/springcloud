package com.seasy.springcloud.serviceconsumer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

@RestController
public class UserController {
	//服务提供者的接口地址：此处用应用名
	private String baseURL = "http://service-provider-1";
	
	@Autowired
    private RestTemplate restTemplate;

	/**
	 * 通过@HystrixCommand注解为业务方法指定回退方法
	 * 		fallbackMethod: 回退方法
	 * 		commandProperties: 指定Hystrix的隔离策略
	 */
	@HystrixCommand(fallbackMethod = "getUserById_Fallback",
			commandProperties={
					@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
			})
	@GetMapping("/getUserById/{id}")
	public String getUserById(@PathVariable Long id){
		String result = restTemplate.getForObject(baseURL + "/user/{id}", String.class, id);
		return result;
	}
	
	public String getUserById_Fallback(Long id){
		String msg = "请求异常，执行回退方法";
		System.out.println(msg);
		return msg;
	}
	
	@GetMapping("/user/{id}")
	public String addUser(@PathVariable Long id){
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
		
		return System.currentTimeMillis() + " >> consumer1 >> " + result;
	}
	
}
