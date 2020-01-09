package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Customer;
import com.example.bean.Products;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;

@Service
public class CommonService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductRepository productRepository;

	public String login(String name, String password, ModelAndView mav, HttpSession session) {
		Customer customer = customerRepository.findByNameAndPassword(name, password);
		if (customer != null) {
			session.setAttribute("customer", customer);
			session.setMaxInactiveInterval(100);
			List<Products> list = productRepository.findAll();
			session.setAttribute("products", list);
			mav.setViewName("web");
		} else {
			mav.setViewName("login");
			mav.addObject("message", "Username or password incorrect!");
		}
		return "redirect:web";
	}

	public ModelAndView register(Customer customer, ModelAndView mav) {
		if (!(customer.getPassword().isBlank() && customer.getRpassword().isBlank())) {
			mav.setViewName("register");
			List<Customer> list = customerRepository.findByName(customer.getName());
			if (list.isEmpty()) {
				customerRepository.save(customer);
				mav.addObject("success", "Successful!");
			} else {
				mav.addObject("fail", "User already exists!");
			}
		} else {
			mav.addObject("fail", "Passwords do not match!");
		}
		return mav;
	}

	public List<Products> getProducts() {
		return productRepository.findAll();
	}

	public void begin() {
		Products ps = new Products();
		ps.setName("qwer");
		ps.setPrice(15);
		productRepository.save(ps);
		Products products = productRepository.findById(1).get();
		List<Products> p = new ArrayList<Products>();
		products.setQuantity(1000);
		p.add(products);
		Customer c = new Customer();
		c.setName("root");
		c.setPassword("root");
		List<String> add = new ArrayList<String>();
		add.add("first add");
		add.add("second add");
		c.setAddress(add);
		c.setCart(p);
		List<String> trans = new ArrayList<String>();
		trans.add("first tran");
		trans.add("second tran");
		c.setTransaction(trans);
		customerRepository.save(c);

	}

	public void addToCart(Products product, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		List<Products> updateCart = new ArrayList<Products>();
		if (product.getQuantity() > 0) {
			Customer customer = (Customer) session.getAttribute("customer");
			if (customer.getCart() != null) {
				List<Products> cart = customer.getCart();
				for (Products products : cart) {
					if (product.getId() == products.getId()) {
						products.setId(products.getQuantity() + product.getQuantity());
					}
					updateCart.add(products);
				}
			}
		}
		System.out.println(updateCart);
	}

	public ModelAndView logout(ModelAndView mav, HttpSession session) {
		mav.setViewName("login");
		Customer customer = (Customer) session.getAttribute("employee");
		if (customer != null) {
			session.invalidate();
			mav.addObject("logout", "Sucessfully logged out!");
		} else {
			mav.addObject("message", "Please login first!");
		}
		return mav;
	}

	public ModelAndView web(HttpServletRequest request, ModelAndView mav) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mav.setViewName("login");
			mav.addObject("message", "Session timed out, Please login again!");
		} else {
			mav.setViewName("web");
		}
		return mav;
	}
}
