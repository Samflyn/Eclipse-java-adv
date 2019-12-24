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
import com.test.service.TaskService;

@Controller
public class TasksController {

	@RequestMapping(value = "tasks", method = RequestMethod.POST)
	public ModelAndView tasks(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		String status = request.getParameter("status_value");
		if (!status.isBlank()) {
			String result = TaskService.setStatus(name, status);
			if (result.equals("success")) {
				mv.setViewName("tasks");
				mv.addObject("success", "Sucessfully updated status!");
			} else if (result.equals("fail")) {
				mv.setViewName("tasks");
				mv.addObject("fail", "Failed updating status!");
			} else {
				mv.setViewName("tasks");
				mv.addObject("fail", result);
			}
		} else {
			mv.setViewName("tasks");
			mv.addObject("fail", "Please set a status first!");
		}
		return mv;
	}

	@RequestMapping(value = "createtask", method = RequestMethod.POST)
	public ModelAndView createtask(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String task = request.getParameter("task");
		int id = Integer.parseInt(request.getParameter("employee"));
		String assignedby = request.getParameter("assignedby");
		if (!task.isBlank()) {
			String result = TaskService.setTask(id, task, assignedby);
			if (result.equals("success")) {
				mv.setViewName("createtask");
				mv.addObject("success", "Sucessfully updated status!");
			} else if (result.equals("fail")) {
				mv.setViewName("createtask");
				mv.addObject("fail", "Failed updating status!");
			} else {
				mv.setViewName("createtask");
				mv.addObject("fail", result);
			}
		} else {
			mv.setViewName("createtask");
			mv.addObject("fail", "Please set a task first!");
		}
		return mv;
	}

	@RequestMapping(value = "removetask", method = RequestMethod.POST)
	public String removetask(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String result = TaskService.removeTask(name);
		return result;
	}

	@RequestMapping(value = "getstatus", method = RequestMethod.GET)
	public ModelAndView getstatus(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		if (name != null) {
			List task = TaskService.getStatus(name);
			if (task != null) {
				mv.setViewName("status");
				mv.addObject("result", task);
			} else {
				mv.setViewName("status");
				mv.addObject("ok", "No task Assigned yet");
			}
		} else {
			mv.setViewName("login");
			mv.addObject("fail", "Please login first!");
		}
		return mv;
	}
}
