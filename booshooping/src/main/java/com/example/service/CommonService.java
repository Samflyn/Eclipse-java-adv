package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bean.Cart;
import com.example.bean.Customer;
import com.example.repository.CustomerRepository;

@Service
public class CommonService {
	@Autowired
	CustomerRepository customerRepository;

//
//	@Autowired
//	ProductRepository productRepository;
//
//	public String login(String name, String password, ModelAndView mav, HttpSession session) {
//		Customer customer = customerRepository.findByNameAndPassword(name, password);
//		if (customer != null) {
//			session.setAttribute("customer", customer);
//			session.setMaxInactiveInterval(100);
//			List<Products> Collection = productRepository.findAll();
//			session.setAttribute("products", Collection);
//			mav.setViewName("web");
//			System.out.println(customer.getCart());
//		} else {
//			mav.setViewName("login");
//			mav.addObject("message", "Username or password incorrect!");
//		}
//		return "redirect:web";
//	}
//
//	public ModelAndView register(Customer customer, ModelAndView mav) {
//		if (!(customer.getPassword().isBlank() && customer.getRpassword().isBlank())) {
//			mav.setViewName("register");
//			List<Customer> Collection = customerRepository.findByName(customer.getName());
//			if (Collection.isEmpty()) {
//				customerRepository.save(customer);
//				mav.addObject("success", "Successful!");
//			} else {
//				mav.addObject("fail", "User already exists!");
//			}
//		} else {
//			mav.addObject("fail", "Passwords do not match!");
//		}
//		return mav;
//	}
//
//	public List<Products> getProducts() {
//		return productRepository.findAll();
//	}
//
	@Transactional
	public void begin() {
		List<Customer> list = customerRepository.findAll();
		Customer customer = list.get(0);
		List<Cart> cart = new ArrayList<Cart>();
		Cart c = new Cart();
		c.setName("listt");
		c.setPrice(11);
		c.setQuantity(16435);
		cart.add(c);
		cart.add(customer.getCart().get(0));
		customer.setCart(cart);
		customerRepository.save(customer);
	}
//
//	@Transactional
//	public void addToCart(Products product, HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if (session != null) {
//			Customer customer = (Customer) session.getAttribute("customer");
//			List<Products> updateCart = new ArrayList<Products>();
//			if (!customer.getCart().isEmpty()) {
//				if (product.getQuantity() > 0) {
//					List<Products> cart = customer.getCart();
//
//					Optional<Products> isProductExist = cart.stream().filter(c -> c.getId() == product.getId())
//							.findAny();
//					if (isProductExist.isPresent()) {
//						Products products = isProductExist.get();
//						products.setQuantity(products.getQuantity() + product.getQuantity());
//
//					}
//
//					for (Products products : cart) {
//						if (product.getId() == products.getId()) {
//							products.setQuantity(products.getQuantity() + product.getQuantity());
//						}
//						updateCart.add(products);
//					}
//				}
//			} else {
//				updateCart.add(product);
//			}
//			Collection<Products> update = updateCart;
//			System.out.println();
//			customerRepository.save();
//		}
//	}	 
//
//	public ModelAndView logout(ModelAndView mav, HttpSession session) {
//		mav.setViewName("login");
//		Customer customer = (Customer) session.getAttribute("employee");
//		if (customer != null) {
//			session.invalidate();
//			mav.addObject("logout", "Sucessfully logged out!");
//		} else {
//			mav.addObject("message", "Please login first!");
//		}
//		return mav;
//	}
//
//	public ModelAndView web(HttpServletRequest request, ModelAndView mav) {
//		HttpSession session = request.getSession(false);
//		if (session == null) {
//			mav.setViewName("login");
//			mav.addObject("message", "Session timed out, Please login again!");
//		} else {
//			mav.setViewName("web");
//		}
//		return mav;
//	}
//
//	public ModelAndView getCart(ModelAndView mav, HttpSession session) {
//
//		return mav;
//	}

}
