package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.service.LoginService;

@Controller
public class LoginController {

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String role = LoginService.authenticate(name, password);
		if (role != null) {
			mv = new ModelAndView();
			mv.setViewName("web");
			mv.addObject("role", role);
			mv.addObject("name", name);
			HttpSession session = request.getSession();
			mv.addObject("session", session);
			return mv;
		} else if (role.equals("error")) {
			mv = new ModelAndView();
			mv.setViewName("login");
			mv.addObject("message", "Server Busy");
			return mv;
		} else {
			mv = new ModelAndView();
			mv.setViewName("login");
			mv.addObject("message", "Wrong username or password");
			return mv;
		}
	}
}
