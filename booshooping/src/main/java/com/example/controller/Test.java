package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Address;
import com.example.bean.Cart;
import com.example.bean.Customer;
import com.example.bean.Products;
import com.example.service.CommonService;
import com.example.service.UserDetailsImpl;

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

	@PostMapping("/register")
	public ModelAndView signup(Customer customer, ModelAndView mav) {
		return service.register(customer, mav);
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(ModelAndView mav) {
		return mav;
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
	public ModelAndView updateProfile(Customer customer, ModelAndView mav) {
		return service.updateProfile(customer, mav);
	}

	@GetMapping("/category")
	public ModelAndView category(ModelAndView mav) {
		return service.category(mav);
	}

	@GetMapping("/shop")
	public ModelAndView category(String id, ModelAndView mav) {
		return service.category(id, mav);
	}

	@PostMapping("/tocart")
	public void addToCart(Cart cart, ModelAndView mav) {
		service.addToCart(cart, mav);
	}

	@PostMapping("/removecart")
	public void removeCart(Cart cart) {
		service.removeCart(cart);
	}

	@PostMapping("/minus")
	public void minus(Cart cart) {
		service.minus(cart);
	}

	@PostMapping("/plus")
	public void plus(Cart cart) {
		service.plus(cart);
	}

	@GetMapping("/mycart")
	public ModelAndView getCart(ModelAndView mav) {
		return service.getCart(mav);
	}

	@GetMapping("/address")
	public ModelAndView getAddress(ModelAndView mav) {
		return service.getAddress(mav);
	}

	@PostMapping("/addaddress")
	public void addAddress(Address address) {
		service.addAddress(address);
	}

	@PostMapping("/removeaddress")
	public void removeAddress(Integer id) {
		service.removeAddress(id);
	}

	@PostMapping("/pay")
	public ModelAndView pay(Integer total, String address, String add, ModelAndView mav) {
		return service.pay(total, address, add, mav);
	}

	@GetMapping("/orders")
	public ModelAndView getOrders(ModelAndView mav) {
		return service.getOrders(mav);
	}

	@PostMapping("/item")
	public ModelAndView getItem(Integer id, ModelAndView mav) {
		return service.getItem(id, mav);
	}

	@GetMapping("/addproduct")
	public ModelAndView addProduct(ModelAndView mav) {
		return service.addProduct(mav);
	}

	@PostMapping("/addproducts")
	public ModelAndView addProducts(Products product, ModelAndView mav) {
		return service.addProducts(product, mav);
	}

	@PostMapping("/delproduct")
	public void delProduct(Integer pid) {
		service.delProduct(pid);
	}

	@GetMapping("/manage")
	public ModelAndView manage(ModelAndView mav) {
		return service.manage(mav);
	}

	@GetMapping("/editproduct")
	public ModelAndView editProduct(Integer id, ModelAndView mav) {
		return service.editProduct(id, mav);
	}

	@PostMapping("/editproduct")
	public String editProduct(Products product, ModelAndView mav) {
		return service.editProduct(product, mav);
	}
}
