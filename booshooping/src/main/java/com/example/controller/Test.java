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
import com.example.bean.Products;
import com.example.service.CommonService;

@Controller
public class Test {
	@Autowired
	CommonService service;

	@GetMapping
	public ModelAndView start(ModelAndView mav) {
		mav.setViewName("start");
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

	@PostMapping("/login")
	public String authenticate(String email, String password, ModelAndView mav, HttpSession session) {
		return service.login(email, password, mav, session);
	}

	@PostMapping("/register")
	public ModelAndView signup(Customer customer, ModelAndView mav) {
		return service.register(customer, mav);
	}

	@GetMapping("/logout")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		return service.logout(mav, request);
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(ModelAndView mav, HttpServletRequest request) {
		return service.dashboard(mav, request);
	}

	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}

	@GetMapping("/editProfile")
	public String edit() {
		return "editprofile";
	}

	@PostMapping("/updateProfile")
	public ModelAndView updateProfile(Customer customer, ModelAndView mav, HttpServletRequest request) {
		return service.updateProfile(customer, mav, request);
	}

	@GetMapping("/category")
	public ModelAndView category(ModelAndView mav, HttpServletRequest request) {
		return service.category(mav, request);
	}

	@GetMapping("/shop")
	public String category(String id, ModelAndView mav, HttpServletRequest request) {
		return service.category(id, mav, request);
	}

	@GetMapping("/productlist")
	public ModelAndView productList(ModelAndView mav, HttpServletRequest request) {
		return service.productList(mav, request);
	}

	@PostMapping("/tocart")
	public void addToCart(Cart cart, HttpServletRequest request, ModelAndView mav) {
		service.addToCart(cart, request, mav);
	}

	@PostMapping("/removecart")
	public void removeCart(Cart cart, HttpServletRequest request) {
		service.removeCart(cart, request);
	}

	@PostMapping("/minus")
	public void minus(Cart cart, HttpServletRequest request) {
		service.minus(cart, request);
	}

	@PostMapping("/plus")
	public void plus(Cart cart, HttpServletRequest request) {
		service.plus(cart, request);
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
	public ModelAndView pay(Integer total, String address, String add, HttpServletRequest request, ModelAndView mav) {
		return service.pay(total, address, add, request, mav);
	}

	@GetMapping("/orders")
	public ModelAndView getOrders(ModelAndView mav, HttpServletRequest request) {
		return service.getOrders(mav, request);
	}

	@PostMapping("/item")
	public ModelAndView getItem(Integer id, ModelAndView mav, HttpServletRequest request) {
		return service.getItem(id, mav, request);
	}

	@GetMapping("/addproduct")
	public ModelAndView addProduct(ModelAndView mav, HttpServletRequest request) {
		return service.addProduct(mav, request);
	}

	@PostMapping("/addproducts")
	public ModelAndView addProducts(Products product, ModelAndView mav, HttpServletRequest request) {
		return service.addProducts(product, mav, request);
	}

	@PostMapping("/delproduct")
	public void delProduct(Integer pid, HttpServletRequest request) {
		service.delProduct(pid, request);
	}

	@GetMapping("/manage")
	public ModelAndView manage(ModelAndView mav, HttpServletRequest request) {
		return service.manage(mav, request);
	}

	@GetMapping("/editproduct")
	public ModelAndView editProduct(Integer id, ModelAndView mav, HttpServletRequest request) {
		return service.editProduct(id, mav, request);
	}

	@PostMapping("/editproduct")
	public String editProduct(Products product, ModelAndView mav, HttpServletRequest request) {
		return service.editProduct(product, mav, request);
	}
}
