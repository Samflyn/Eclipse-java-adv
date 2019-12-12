package com.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.bean.SamEmployees;
import com.test.service.RegisterService;

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
		SamEmployees se = new SamEmployees();
		se.setId(Integer.parseInt(request.getParameter("manager")));
		if (password.equals(rpassword)) {
			SamEmployees s = new SamEmployees();
			s.setName(name);
			s.setPassword(password);
			s.setDob(date);
			s.setGender(gender);
			s.setRole(role);
			s.setManager(se);
			String register = RegisterService.register(s);
			mv = new ModelAndView("register");
			if (register.equals("fail")) {
				mv.addObject("fail", "Failed to register!");
			} else {
				mv.addObject("success", "Registered Sucessfully!");
			}
			return mv;
		} else {
			mv = new ModelAndView("register");
			mv.addObject("message", "Passwords do not match");
			return mv;
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws IOException {
		int i = Integer.parseInt(request.getParameter("name"));
		String result = RegisterService.delete(i);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result);		
	}
}
