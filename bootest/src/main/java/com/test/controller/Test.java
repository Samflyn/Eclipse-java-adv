package com.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.test.bean.Employee;
import com.test.service.EmployeeService;

@Controller
public class Test implements ErrorController {
	@Autowired
	EmployeeService service;

	@GetMapping
	public String start() {
		return "start";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/login")
	public String authenticate(String name, String password, ModelAndView mv, HttpSession session) {
		mv = service.login(name, password, mv, session);
		return "redirect:web";
	}

	@GetMapping("/web")
	public ModelAndView web(HttpServletRequest request, ModelAndView mv) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mv.setViewName("login");
			mv.addObject("message", "Session timed out, Please login again!");
		}
		return mv;
	}

	@PostMapping("/register")
	public ModelAndView signup(Employee e, ModelAndView mv) {
		mv = service.register(e, mv);
		return mv;
	}

	@GetMapping("/list")
	public ModelAndView list(ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mv.setViewName("login");
			mv.addObject("message", "Session timed out, Please login again!");
		} else {
			mv = service.list(mv);
		}
		return mv;
	}

	@GetMapping("/update")
	public ModelAndView update(Integer id, ModelAndView mv, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mv.setViewName("login");
			mv.addObject("message", "Session timed out, Please login again!");
		} else {
			mv = service.update(id, mv, session);
		}
		return mv;
	}

	@PostMapping("/update")
	public ModelAndView edit(Employee e, ModelAndView mv) {
		service.save(e);
		mv.setViewName("update");
		mv.addObject("success", "Updated sucessfully!");
		return mv;
	}

	@PostMapping("/delete")
	public void delete(Integer id) {
		service.delete(id);
	}

	@GetMapping("/logout")
	public ModelAndView logout(ModelAndView mv, HttpSession session) {
		mv.setViewName("login");
		Employee emp = (Employee) session.getAttribute("employee");
		if (emp != null) {
			session.invalidate();
			mv.addObject("logout", "Sucessfully logged out!");
		} else {
			mv.addObject("message", "Please login first!");
		}
		return mv;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}

}
