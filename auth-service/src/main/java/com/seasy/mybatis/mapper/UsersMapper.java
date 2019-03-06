package com.seasy.mybatis.mapper;

import java.util.List;

import com.seasy.mybatis.entity.RolesEntity;
import com.seasy.mybatis.entity.UsersEntity;

public interface UsersMapper{
	 public List<UsersEntity> selectByLoginName(String loginName);
	 public List<RolesEntity> getAllRoleByUserId(Long userId);
}
