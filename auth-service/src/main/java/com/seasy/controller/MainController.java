package com.seasy.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.common.LoggerFactory;
import com.seasy.common.ResponseBean;
import com.seasy.encryptionpolicy.EncryptionResult;
import com.seasy.mybatis.entity.UserEntity;
import com.seasy.service.UserService;
import com.seasy.utils.JWTUtil;

@RestController
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
    StringRedisTemplate redisTemplate;
	
	/**
	 * 登录认证
	 * @param username 用户名
	 * @param password 密码
	 */
	@GetMapping("/login")
	public ResponseBean login(@RequestParam String username, @RequestParam String password) {
		UserEntity userEntity = userService.getUserByLoginName(username);
		if(userEntity == null){
			return new ResponseBean(200, "user not found");
		}else{
			EncryptionResult result = userService.getEncryptionPolicy().encrypt(password, userEntity.getSalt());
			if(!result.getPassword().equals(userEntity.getPassword())){
				return new ResponseBean(200, "username or password error");
			}
		}
		
		//认证成功后，生成token返回给客户端
		String token = JWTUtil.generateToken(userEntity.getLoginName());
		logger.debug(token);
		
		//将token放入redis，并设置token的过期时间
		redisTemplate.opsForValue().set(token, userEntity.getRoles(), 
				JWTUtil.DEFAULT_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
		
		return new ResponseBean(200, "login success", token);
	}

    @GetMapping("/")
    public String index() {
    	return "auth-service: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
}
