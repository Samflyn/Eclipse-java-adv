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
		if (!(name.isBlank() || password.isBlank())) {
			String role = LoginService.authenticate(name, password);
			if (!role.equals("failed")) {
				mv = new ModelAndView("web");
				mv.addObject("role", role);
				mv.addObject("name", name);
				HttpSession session = request.getSession();
				session.setAttribute("role", role);
				session.setAttribute("name", name);
				mv.addObject("session", session);
			} else if (role.equals("error")) {
				mv = new ModelAndView("login");
				mv.addObject("message", "Server Busy!");
			} else {
				mv = new ModelAndView("login");
				mv.addObject("message", "Wrong username or password!");
			}
		} else {
			mv = new ModelAndView("login");
			mv.addObject("message", "Wrong username or password!");
		}
		return mv;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			mv = new ModelAndView("login");
			mv.addObject("logout", "Sucessfully logged out!");
		} else {
			mv = new ModelAndView("login");
			mv.addObject("message", "Please login first!");			
		}
		return mv;
	}
}
