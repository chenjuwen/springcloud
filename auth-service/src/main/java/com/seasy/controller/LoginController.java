package com.seasy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	@RequestMapping("/")
    public String home() {
        return "forward:/login";
    }
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, ModelMap model) {
		return "login";
	}
	
	@RequestMapping("/index")
    public String index() {
        return "index";
    }
	
	@RequestMapping("/admin/{id}")
	@ResponseBody
    public String getUser(@PathVariable String id) {
        return id;
    }
}
