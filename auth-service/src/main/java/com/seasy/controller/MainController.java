package com.seasy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seasy.common.ResponseBean;
import com.seasy.encryptionpolicy.EncryptionResult;
import com.seasy.mybatis.entity.UserEntity;
import com.seasy.service.UserService;
import com.seasy.utils.StringUtil;

@RestController
public class MainController {
	@Autowired
	private UserService userService;
	
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
		return new ResponseBean(200, "login success");
	}
	
	@GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "already login");
        } else {
            return new ResponseBean(200, "guest user");
        }
    }
	
	@GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "require role: admin");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical=Logical.AND, value={"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized");
    }
    
}
