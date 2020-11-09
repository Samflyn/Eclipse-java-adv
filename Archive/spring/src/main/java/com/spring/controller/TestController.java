package com.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.service.AddService;

@Controller
public class TestController {
	@RequestMapping("/app")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
		int i = Integer.parseInt(request.getParameter("t1"));
		int j = Integer.parseInt(request.getParameter("t2"));
		AddService as = new AddService();
		int k = as.add(i, j);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("display.jsp");
		mv.addObject("result", k);
		return mv;
	}

	public ModelAndView test1(@RequestParam("t1") int i, @RequestParam("t2") int j) {
		ModelAndView mv = new ModelAndView();
		AddService as = new AddService();
		int k = as.add(i, j);
		mv.setViewName("display");
		mv.addObject("result", k);
		return mv;
	}
}
