package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.User;
import com.demo.service.PublicService;

@Controller
@RequestMapping("/")
public class PublicController {
	@Autowired
	PublicService publicService;

	@GetMapping
	public String start() {
		return "start";
	}

	@GetMapping("register")
	public String register() {
		return "register";
	}

	@PostMapping("register")
	public ModelAndView register(User user, ModelAndView mav) {
		return publicService.register(user, mav);
	}
}
