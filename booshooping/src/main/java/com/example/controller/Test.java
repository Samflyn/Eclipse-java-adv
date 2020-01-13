package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Address;
import com.example.bean.Cart;
import com.example.bean.Customer;
import com.example.service.CommonService;

@Controller
public class Test {
	@Autowired
	CommonService service;

	@GetMapping
	public ModelAndView start(ModelAndView mav) {
		mav.setViewName("start");
		// service.begin();
		return mav;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public ModelAndView signup(Customer customer, ModelAndView mav) {
		return service.register(customer, mav);
	}

	@PostMapping("/login")
	public String authenticate(String name, String password, ModelAndView mav, HttpSession session) {
		return service.login(name, password, mav, session);
	}

	@GetMapping("/web")
	public ModelAndView web(ModelAndView mav, HttpServletRequest request) {
		return service.web(mav, request);
	}

	@GetMapping("/logout")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		return service.logout(mav, request);
	}

	@PostMapping("/tocart")
	public ModelAndView addToCart(Cart cart, HttpServletRequest request, ModelAndView mav) {
		return service.addToCart(cart, request, mav);
	}

	@PostMapping("/removecart")
	public void removeCart(Cart cart, HttpServletRequest request) {
		service.removeCart(cart, request);
	}

	@PostMapping("/minus")
	public void minus(Cart cart, HttpServletRequest request) {
		service.minus(cart, request);
	}

	@GetMapping("/mycart")
	public ModelAndView getCart(ModelAndView mav, HttpServletRequest request) {
		return service.getCart(mav, request);
	}

	@GetMapping("/address")
	public ModelAndView getAddress(ModelAndView mav, HttpServletRequest request) {
		return service.getAddress(mav, request);
	}

	@PostMapping("/addaddress")
	public void addAddress(Address address, HttpServletRequest request) {
		service.addAddress(address, request);
	}

	@PostMapping("/removeaddress")
	public void removeAddress(Integer id, HttpServletRequest request) {
		service.removeAddress(id, request);
	}

	@PostMapping("/pay")
	public ModelAndView pay(Integer total, String address, HttpServletRequest request, ModelAndView mav) {
		return service.pay(total, address, request, mav);
	}

	@GetMapping("/orders")
	public ModelAndView getOrders(ModelAndView mav, HttpServletRequest request) {
		return service.getOrders(mav, request);
	}
}
