package com.seasy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.seasy.encryptionpolicy.DefaultEncryptionPolicy;
import com.seasy.encryptionpolicy.EncryptionPolicy;
import com.seasy.mybatis.entity.RolesEntity;
import com.seasy.mybatis.entity.UsersEntity;
import com.seasy.mybatis.mapper.UsersMapper;

@Service("userService")
public class UserService {
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private EncryptionPolicy encryptionPolicy;

	public UsersEntity getUserByLoginName(String loginName) {
		List<UsersEntity> userList = usersMapper.getUserByLoginName(loginName);
		if(!CollectionUtils.isEmpty(userList)){
			return userList.get(0);
		}
		return null;
	}
	
	public List<RolesEntity> getAllRoleByUserId(Long userId) {
		return usersMapper.getAllRoleByUserId(userId);
	}

	public EncryptionPolicy getEncryptionPolicy() {
		return encryptionPolicy;
	}

	@Bean
	public EncryptionPolicy encryptionPolicy(){
		return new DefaultEncryptionPolicy();
	}
	
}