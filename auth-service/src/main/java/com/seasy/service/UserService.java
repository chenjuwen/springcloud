package com.seasy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.seasy.encryptionpolicy.DefaultEncryptionPolicy;
import com.seasy.encryptionpolicy.EncryptionPolicy;
import com.seasy.mybatis.entity.RoleEntity;
import com.seasy.mybatis.entity.UserEntity;
import com.seasy.mybatis.mapper.UsersMapper;

@Service
public class UserService {
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private EncryptionPolicy encryptionPolicy;

	public UserEntity getUserByLoginName(String loginName) {
		UserEntity user = null;
		
		List<UserEntity> userList = usersMapper.getUserByLoginName(loginName);
		if(!CollectionUtils.isEmpty(userList)){
			user = userList.get(0);
			
			List<RoleEntity> roleList = usersMapper.getAllRoleByUserId(user.getId());
			if(!CollectionUtils.isEmpty(roleList)){
				for(RoleEntity role : roleList){
					user.getRoleList().add(role.getRoleNo());
				}
			}
		}
		return user;
	}

	public EncryptionPolicy getEncryptionPolicy() {
		return encryptionPolicy;
	}

	@Bean
	public EncryptionPolicy encryptionPolicy(){
		return new DefaultEncryptionPolicy();
	}
	
}