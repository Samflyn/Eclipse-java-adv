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
			session.setAttribute("message", "Invalid Email or password!");
			session.setMaxInactiveInterval(5);
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
	public void addToCart(Cart cart, HttpServletRequest request, ModelAndView mav) {
		Boolean contains = false;
		HttpSession session = request.getSession(false);
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("customer");
			customer = customerRepository.findById(customer.getId()).get();
			List<Cart> updateCart = new ArrayList<Cart>();
			if (!customer.getCart().isEmpty()) {
				List<Cart> customerCart = customer.getCart();
				for (Cart each : customerCart) {
					if (each.getProductid() == cart.getProductid()) {
						each.setQuantity(each.getQuantity() + cart.getQuantity());
						contains = true;
					}
					updateCart.add(each);
				}
				if (!contains) {
					updateCart.add(cart);
				}
				customer.setCart(updateCart);
			} else {
				updateCart.add(cart);
				customer.setCart(updateCart);
			}
			customerRepository.save(customer);
			session.setAttribute("customer", customer);
		}
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
			customer = customerRepository.findById(customer.getId()).get();
			List<Cart> cart = customer.getCart();
			List<Cart> noProduct = new ArrayList<Cart>();
			List<Cart> noStock = new ArrayList<Cart>();
			List<Products> stock = new ArrayList<Products>();
			List<Integer> cartId = new ArrayList<Integer>();
			List<Products> products = productRepository.findAllById(cartId);
			ArrayList<Integer> pid = new ArrayList<Integer>();
			for (Products p : products) {
				pid.add(p.getId());
				for (Cart c : cart) {
					cartId.add(c.getProductid());
					if (c.getProductid() == p.getId()) {
						if (c.getQuantity() > p.getStock()) {
							noStock.add(c);
							stock.add(p);
						}
					}
				}
			}
			for (Cart c : cart) {
//				if (!productRepository.existsById(c.getProductid()))
//					noproduct.add(c);
				if (!pid.contains(c.getProductid()))
					noProduct.add(c);
			}
			if (noProduct.isEmpty()) {
				if (noStock.isEmpty()) {
					List<Items> items = new ArrayList<Items>();
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
							Address a = new Address(address);
							list.add(a);
							customer.setAddress(list);
							customerRepository.save(customer);
						}
					}
					List<Products> update = new ArrayList<Products>();
					for (Cart temp : cart) {
						Items item = new Items(temp.getName(), temp.getPrice(), temp.getQuantity());
						items.add(item);
						for (Products p : products) {
							if (temp.getProductid() == p.getId()) {
								p.setStock(p.getStock() - temp.getQuantity());
								update.add(p);
							}
						}
					}
					productRepository.saveAll(update);
					Date date = new Date();
					Transactions tx = new Transactions(UUID.randomUUID().toString(), date.toString(), address, items,
							total, "Success");
					List<Transactions> transactions = customer.getTransactions();
					transactions.add(tx);
					customer.setCart(null);
					customer.setTransactions(transactions);
					customerRepository.save(customer);
					String day = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MMM-YYYY"));
					session.setAttribute("tx", tx);
					session.setAttribute("day", day);
					mav.addObject("tx", tx);
					mav.setViewName("payment");
				} else {
					mav.setViewName("nostock");
					mav.addObject("carts", noStock);
					mav.addObject("products", stock);
				}
			} else {
				mav.setViewName("noproducts");
				mav.addObject("products", noProduct);
			}
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

	public ModelAndView addProducts(Products product, ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			productRepository.save(product);
			mav.setViewName("addproducts");
			mav.addObject("message", "Sucessfully added product to list!");
		}
		return mav;
	}

	public void delProduct(Integer id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			productRepository.deleteById(id);
		}
	}

	public ModelAndView manage(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			List<Products> list = productRepository.findAll();
			mav.setViewName("manage");
			mav.addObject("list", list);
		}
		return mav;
	}

	public ModelAndView addProduct(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			List<Products> list = productRepository.findAll();
			TreeSet<String> category = new TreeSet<String>();
			for (Products product : list) {
				category.add(product.getCategory());
			}
			mav.setViewName("addproducts");
			mav.addObject("category", category);
		}
		return mav;
	}

	public ModelAndView editProduct(Integer id, ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return toLogin(mav);
		} else {
			Products product = productRepository.findById(id).get();
			mav.setViewName("editproduct");
			mav.addObject("product", product);
		}
		return mav;
	}

	public String editProduct(Products product, ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			productRepository.save(product);
		}
		return "redirect:manage";
	}
}