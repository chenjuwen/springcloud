package com.seasy.mybatis.mapper;

import java.util.List;

import com.seasy.mybatis.entity.RoleEntity;
import com.seasy.mybatis.entity.UserEntity;

public interface UsersMapper{
	 public List<UserEntity> getUserByLoginName(String loginName);
	 public List<RoleEntity> getAllRoleByUserId(Long userId);
}
