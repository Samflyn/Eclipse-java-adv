package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String rpassword = request.getParameter("rpassword");
		String date = request.getParameter("date");
		String gender = request.getParameter("gender");
		String role = request.getParameter("role");
		
		if (password.equals(rpassword)) {
			mv = new ModelAndView();
			mv.setViewName("register");
			mv.addObject("message", "Passwords do not match");
			return mv;
		} else {
			mv = new ModelAndView();
			mv.setViewName("register");
			mv.addObject("message", name);
			return mv;
		}
	}
}
