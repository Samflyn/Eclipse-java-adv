package com.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.bean.SamEmployees;
import com.test.service.ListService;

@Controller
public class HandlerController {

	@RequestMapping(value = "reg", method = RequestMethod.GET)
	public ModelAndView reg(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		try {
			List list = ListService.getList();
			mv.setViewName("register");
			mv.addObject("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "etask", method = RequestMethod.GET)
	public ModelAndView etask(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		try {
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("name");
			SamEmployees result = ListService.getResult(name);
			mv.setViewName("tasks");
			mv.addObject("result", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "ctask", method = RequestMethod.GET)
	public ModelAndView ctask(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		try {
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("name");
			List list = ListService.getList();
			mv.setViewName("createtask");
			mv.addObject("list", list);
			mv.addObject("name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "liss", method = RequestMethod.GET)
	public ModelAndView liss(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		try {
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("name");
			List list = ListService.getList();
			mv.setViewName("list");
			mv.addObject("list", list);
			mv.addObject("name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
