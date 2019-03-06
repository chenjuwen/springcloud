package com.seasy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/")
    public String index() {
        return "forward:/login";
    }
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, ModelMap model) {
		return "login";
	}
}
