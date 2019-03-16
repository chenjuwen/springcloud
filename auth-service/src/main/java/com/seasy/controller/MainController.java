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

import com.seasy.common.AuthResult;
import com.seasy.common.LoggerFactory;
import com.seasy.encryptionpolicy.EncryptionResult;
import com.seasy.mybatis.entity.UserEntity;
import com.seasy.service.UserService;
import com.seasy.utils.JWTUtil;
import com.seasy.utils.StringUtil;

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
	public AuthResult login(@RequestParam String username, @RequestParam String password) {
		UserEntity userEntity = userService.getUserByLoginName(username);
		if(userEntity == null){
			return new AuthResult(1001, "username or password error");
		}else{
			EncryptionResult result = userService.getEncryptionPolicy().encrypt(password, userEntity.getSalt());
			if(!result.getPassword().equals(userEntity.getPassword())){
				return new AuthResult(1002, "username or password error");
			}
		}
		
		//生成token
		String token = JWTUtil.generateToken(userEntity.getLoginName());
		logger.debug(token);
		
		//生成refreshToken
		String refreshToken = StringUtil.getUUIDString();
		logger.debug(token);
		
		//数据放入redis
		redisTemplate.opsForHash().put(refreshToken, "token", token);
		redisTemplate.opsForHash().put(refreshToken, "username", userEntity.getLoginName());
		redisTemplate.opsForHash().put(refreshToken, "role", userEntity.getRoles());
		
		//设置token的过期时间
		redisTemplate.expire(refreshToken, JWTUtil.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
		
		return new AuthResult(0, "success", token, refreshToken);
	}
	
	/**
	 * 刷新token
	 */
	@GetMapping("/refreshToken")
	public AuthResult refreshToken(@RequestParam String refreshToken) {
		String username = (String)redisTemplate.opsForHash().get(refreshToken, "username");
		if(StringUtil.isEmpty(username)){
			return new AuthResult(1003, "refreshToken has expired");
		}

		//生成新的token
		String newToken = JWTUtil.generateToken(username);
		logger.debug(newToken);

		redisTemplate.opsForHash().put(refreshToken, "token", newToken);
		
		return new AuthResult(0, "success", newToken, refreshToken);
	}

    @GetMapping("/")
    public String index() {
    	return "auth-service: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
}
