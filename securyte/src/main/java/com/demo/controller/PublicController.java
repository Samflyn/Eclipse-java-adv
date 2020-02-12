package com.demo.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasRole('ADMIN')")
	public @interface isAdmin {
	}

	@GetMapping
	@RolesAllowed("ROLE_ADMIN")
	public String start() {
		return "start";
	}

	@GetMapping("register")
	public String register() {
		return "register";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@PostMapping("register")
	public ModelAndView register(User user, ModelAndView mav) {
		return publicService.register(user, mav);
	}
}
