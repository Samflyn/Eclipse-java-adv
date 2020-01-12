package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Address;
import com.example.bean.Cart;
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
		Optional<Customer> optional = customerRepository.findByNameAndPassword(name, password);
		if (!optional.isEmpty()) {
			Customer customer = optional.get();
			session.setAttribute("customer", customer);
			session.setMaxInactiveInterval(100);
			List<Products> products = productRepository.findAll();
			session.setAttribute("products", products);
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
			Optional<Customer> customers = customerRepository.findByName(customer.getName());
			if (customers.isEmpty()) {
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

	@Transactional
	public void begin() {
		Products p = new Products();
		p.setName("Apple");
		p.setAbout("It's a fruit");
		p.setPrice(50);
		productRepository.save(p);
		p = new Products();
		p.setName("Ben & Jerry's");
		p.setAbout("It's a very good ice cream");
		p.setPrice(150);
		productRepository.save(p);
		p = new Products();
		p.setName("Coca Cola");
		p.setAbout("It's the best soft drink");
		p.setPrice(30);
		productRepository.save(p);
		p = new Products();
		p.setName("Docker");
		p.setAbout("It's a phone dock");
		p.setPrice(80);
		productRepository.save(p);
		p = new Products();
		p.setName("Elastic Band");
		p.setAbout("It's an elastic band");
		p.setPrice(20);
		productRepository.save(p);
		List<Address> alist = new ArrayList<Address>();
		Address a = new Address();
		a.setAddress("Flat no:1, Hitex, Hyderabad");
		alist.add(a);
		Customer c = new Customer();
		c.setName("user");
		c.setPassword("user");
		c.setAddress(alist);
		customerRepository.save(c);
	}

	@Transactional
	public ModelAndView addToCart(Cart cart, HttpServletRequest request, ModelAndView mav) {
		System.out.println("add request:");
		System.out.println(cart);
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			System.out.println("session:");
			System.out.println(customer.getCart());
			if (!customer.getCart().isEmpty()) {
				List<Cart> updateCart = new ArrayList<Cart>();
				for (Cart each : customer.getCart()) {
					each.setId(0);
					if (each.getProductid() == cart.getProductid()) {
						each.setQuantity(each.getQuantity() + cart.getQuantity());
						updateCart.add(each);
					} else {
						System.out.println("else");
						updateCart.add(each);
						updateCart.add(cart);
					}
				}
				System.out.println(updateCart);
			} else {
				List<Cart> updateCart = new ArrayList<Cart>();
				updateCart.add(cart);
				customer.setCart(updateCart);
				customerRepository.save(customer);
			}
			session.setAttribute("customer", customer);
		} else {
			mav.setViewName("login");
			mav.addObject("message", "Session timed out, Please login again!");
		}
		return mav;
	}

	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		mav.setViewName("login");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			mav.addObject("logout", "Sucessfully logged out!");
		} else {
			mav.addObject("message", "Please login first!");
		}
		return mav;
	}

	public ModelAndView web(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mav.setViewName("login");
			mav.addObject("message", "Session timed out, Please login again!");
		} else {
			mav.setViewName("web");
		}
		return mav;
	}

	public ModelAndView getCart(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			mav.setViewName("login");
			mav.addObject("message", "Session timed out, Please login again!");
		} else {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Cart> cart = customerRepository.findById(customer.getId()).get().getCart();
			List<Address> address = customer.getAddress();
			session.setAttribute("cart", cart);
			session.setAttribute("address", address);
			mav.setViewName("cart");
		}
		return mav;
	}

}
