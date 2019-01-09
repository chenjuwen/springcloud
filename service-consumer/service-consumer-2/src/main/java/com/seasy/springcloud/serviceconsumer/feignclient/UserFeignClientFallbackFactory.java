package com.seasy.springcloud.serviceconsumer.feignclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.seasy.springcloud.serviceapi.bean.Address;
import com.seasy.springcloud.serviceapi.bean.User;

import feign.hystrix.FallbackFactory;

/**
 * Feign Client的回退工厂类，通过工厂类可以获知回退的具体原因
 */
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient>{
	/**
	 * create方法必须返回Feign Client的接口类
	 */
	@Override
	public UserFeignClient create(Throwable ex) {
		return new UserFeignClient() {
			@Override
			public String addUser(Long id) {
				showErrorReason();
				return "error";
			}

			@Override
			public String query(String name) {
				showErrorReason();
				return "error";
			}

			@Override
			public User getUser(Long id) {
				showErrorReason();
				return new User();
			}

			@Override
			public User add(Address address) {
				showErrorReason();
				return new User();
			}

			@Override
			public String update(Address address) {
				showErrorReason();
				return "error";
			}

			@Override
			public List<Address> getAllAddress() {
				showErrorReason();
				return new ArrayList<Address>();
			}

			@Override
			public void put(Address address) {
				showErrorReason();
				System.out.println("put >> error");
			}
			
			private void showErrorReason(){
				System.out.println(ex.toString());
			}
		};
	}
}
