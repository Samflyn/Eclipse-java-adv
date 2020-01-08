package com.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.User;
import com.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Test {
	@Autowired
	UserService service;

	@GetMapping(path = "/")
	public ModelAndView start(ModelAndView mv) {
		mv.setViewName("start");
		return mv;
	}

	@PostMapping(path = "/register", consumes = "application/json")
	public void register(@RequestBody String data) throws JsonMappingException, JsonProcessingException {
		User user = new ObjectMapper().readValue(data, User.class);
		service.register(user);
	}

	@GetMapping(path = "/all", produces = "application/json")
	public List<User> getUsers() {
		List<User> list = service.getAll();
		return list;
	}

	@GetMapping(path = "{id}", produces = "application/json")
	public Optional<User> getUser(@PathVariable Integer id) {
		Optional<User> user = service.getUser(id);
		return user;
	}

	@DeleteMapping(path = "/delete/{id}", consumes = "application/json")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/delete")
	public ModelAndView deletep(ModelAndView mv) {
		mv.setViewName("delete");
		return mv;
	}

	@GetMapping(path = "/update/{id}", produces = "application/json")
	public ModelAndView update(@PathVariable Integer id, ModelAndView mv) {
		mv.setViewName("update");
		Optional<User> user = service.getUser(id);
		mv.addObject("user", user);
		return mv;
	}

	@PutMapping(path = "/update", consumes = "application/json")
	public void updating(@RequestBody String data) throws JsonMappingException, JsonProcessingException {
		User user = new ObjectMapper().readValue(data, User.class);
		service.register(user);
	}
}