package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Address;
import com.example.bean.Cart;
import com.example.bean.Customer;
import com.example.bean.Items;
import com.example.bean.Products;
import com.example.bean.Transactions;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;

@Service
public class CommonService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductRepository productRepository;

	private ModelAndView toLogin(ModelAndView mav) {
		mav.setViewName("login");
		mav.addObject("message", "Session timed out, Please login again!");
		return mav;
	}

	public String login(String email, String password, ModelAndView mav, HttpSession session) {
		Optional<Customer> optional = customerRepository.findByEmailAndPassword(email, password);
		if (!optional.isEmpty()) {
			Customer customer = optional.get();
			session.setAttribute("customer", customer);
			session.setMaxInactiveInterval(100);
			return "redirect:dashboard";
		} else {
			mav.setViewName("login");
			mav.addObject("message", "Email or password incorrect!");
			return "redirect:login";
		}
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

	public ModelAndView register(Customer customer, ModelAndView mav) {
		if (customer.getPassword().equals(customer.getRpassword())) {
			mav.setViewName("register");
			Optional<Customer> customers = customerRepository.findByEmail(customer.getEmail());
			if (customers.isEmpty()) {
				customerRepository.save(customer);
				mav.addObject("success", "Successfully registered!");
			} else {
				mav.addObject("fail", "Email already exists!");
			}
		} else {
			mav.addObject("fail", "Passwords do not match!");
		}
		return mav;
	}

	public ModelAndView dashboard(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			mav.setViewName("dashboard");
		}
		return mav;
	}

	public ModelAndView category(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			mav.setViewName("category");
			List<Products> all = productRepository.findAll();
			TreeSet<String> ts = new TreeSet<String>();
			for (Products p : all) {
				ts.add(p.getCategory());
			}
			mav.addObject("category", ts);
		}
		return mav;
	}

	@Transactional
	public ModelAndView addToCart(Cart cart, HttpServletRequest request, ModelAndView mav) {
		Boolean contain = false;
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			customer = customerRepository.findById(customer.getId()).get();
			if (!customer.getCart().isEmpty()) {
				List<Cart> updateCart = new ArrayList<Cart>();
				List<Cart> ccart = customer.getCart();
				for (Cart each : ccart) {
					if (each.getProductid() == cart.getProductid()) {
						each.setQuantity(each.getQuantity() + cart.getQuantity());
						contain = true;
					}
					updateCart.add(each);
				}
				if (!contain) {
					updateCart.add(cart);
				}
				customer.setCart(updateCart);
				customerRepository.save(customer);
			} else {
				List<Cart> updateCart = new ArrayList<Cart>();
				updateCart.add(cart);
				customer.setCart(updateCart);
				customerRepository.save(customer);
			}
			session.setAttribute("customer", customer);
		} else {
			return toLogin(mav);
		}
		return mav;
	}

	public ModelAndView productList(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			mav.setViewName("productlist");
		}
		return mav;
	}

	public ModelAndView getCart(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			Customer customer = (Customer) session.getAttribute("customer");
			Optional<Customer> optional = customerRepository.findById(customer.getId());
			if (optional.isPresent()) {
				List<Cart> cart = optional.get().getCart();
				List<Address> address = customer.getAddress();
				session.setAttribute("cart", cart);
				session.setAttribute("address", address);
			}
			mav.setViewName("cart");
		}
		return mav;
	}

	public ModelAndView getAddress(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Address> address = customerRepository.findById(customer.getId()).get().getAddress();
			session.setAttribute("address", address);
			mav.setViewName("addresses");
		}
		return mav;
	}

	public void addAddress(Address address, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Address> list = customerRepository.findById(customer.getId()).get().getAddress();
			List<Address> updateList = new ArrayList<Address>();
			for (Address a : list) {
				updateList.add(a);
			}
			updateList.add(address);
			customer.setAddress(updateList);
			customerRepository.save(customer);
		}
	}

	public void removeCart(Cart cart, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Cart> list = customerRepository.findById(customer.getId()).get().getCart();
			List<Cart> updateCart = new ArrayList<Cart>();
			for (Cart temp : list) {
				if (!(temp.getProductid() == cart.getProductid())) {
					updateCart.add(temp);
				}
			}
			customer.setCart(updateCart);
			customerRepository.save(customer);
		}
	}

	public ModelAndView pay(Integer total, String address, String add, HttpServletRequest request, ModelAndView mav) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			Transactions tx = new Transactions();
			tx.setTxid(UUID.randomUUID().toString());
			Date date = new Date();
			tx.setDate(date.toString());
			List<Items> items = new ArrayList<Items>();
			customer = customerRepository.findById(customer.getId()).get();
			if (add != null && !add.isEmpty()) {
				address = add.trim();
				List<Address> list = customer.getAddress();
				boolean test = false;
				for (Address a : list) {
					if (a.getAddress().contentEquals(address)) {
						test = true;
					}
				}
				if (!test) {
					Address a = new Address();
					a.setAddress(address);
					list.add(a);
					customer.setAddress(list);
					customerRepository.save(customer);
				}
			}
			List<Cart> cart = customer.getCart();
			for (Cart temp : cart) {
				Items item = new Items();
				item.setName(temp.getName());
				item.setPrice(temp.getPrice());
				item.setQuantity(temp.getQuantity());
				items.add(item);
			}
			tx.setItems(items);
			tx.setTotal(total);
			tx.setStatus("Success");
			tx.setAddress(address);
			List<Transactions> transactions = customer.getTransactions();
			transactions.add(tx);
			customer.setCart(null);
			customer.setTransactions(transactions);
			customerRepository.save(customer);
			String day = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MMM"));
			session.setAttribute("tx", tx);
			session.setAttribute("day", day);
			mav.setViewName("payment");
			mav.addObject("tx", tx);
		} else {
			mav.setViewName("login");
			mav.addObject("message", "Session timed out, Please login again!");
		}
		return mav;
	}

	public void minus(Cart cart, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Cart> list = customerRepository.findById(customer.getId()).get().getCart();
			List<Cart> updateCart = new ArrayList<Cart>();
			for (Cart temp : list) {
				if (temp.getProductid() == cart.getProductid()) {
					temp.setQuantity(temp.getQuantity() - 1);
				}
				if (temp.getQuantity() > 0) {
					updateCart.add(temp);
				}
			}
			customer.setCart(updateCart);
			customerRepository.save(customer);
		}
	}

	public ModelAndView getOrders(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			customer = customerRepository.findById(customer.getId()).get();
			session.setAttribute("tx", customer.getTransactions());
			mav.setViewName("myorders");
		} else {
			return toLogin(mav);
		}
		return mav;
	}

	public void removeAddress(Integer id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			customer = customerRepository.findById(customer.getId()).get();
			List<Address> list = customer.getAddress();
			List<Address> updateAddress = new ArrayList<Address>();
			for (Address a : list) {
				if (!(a.getId() == id)) {
					updateAddress.add(a);
				}
			}
			customer.setAddress(updateAddress);
			customerRepository.save(customer);
		}
	}

	public void plus(Cart cart, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			List<Cart> list = customerRepository.findById(customer.getId()).get().getCart();
			List<Cart> updateCart = new ArrayList<Cart>();
			for (Cart temp : list) {
				if (temp.getProductid() == cart.getProductid()) {
					temp.setQuantity(temp.getQuantity() + 1);
				}
				updateCart.add(temp);
			}
			customer.setCart(updateCart);
			customerRepository.save(customer);
		}
	}

	public ModelAndView getItem(Integer id, ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			mav.setViewName("item");
			Customer customer = (Customer) session.getAttribute("customer");
			List<Transactions> list = customerRepository.findById(customer.getId()).get().getTransactions();
			List<Items> item = new ArrayList<Items>();
			for (Transactions t : list) {
				if (t.getId() == id) {
					item = t.getItems();
				}
			}
			mav.addObject("items", item);
		}
		return mav;
	}

	public String category(String category, ModelAndView mav, HttpServletRequest request) {
		List<Products> products = productRepository.findByCategory(category);
		HttpSession session = request.getSession(false);
		session.setAttribute("products", products);
		return "redirect:productlist";
	}

	@Transactional
	public ModelAndView updateProfile(Customer customer, ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			mav.setViewName("editprofile");
			if (customer.getPassword().equals(customer.getRpassword())) {
				customerRepository.updateCustomer(customer.getId(), customer.getName(), customer.getPassword(),
						customer.getDob(), customer.getGender());
				mav.addObject("success", "Sucessfully updated!");
			} else {
				mav.addObject("fail", "Passwords do not match!");
			}
		}
		return mav;
	}

}
