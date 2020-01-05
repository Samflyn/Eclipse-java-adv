package com.test.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.test.bean.Employee;
import com.test.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository repository;

	public List<Employee> getAll() {
		List<Employee> all = repository.findAll();
		return all;
	}

	public void save(Employee e) {
		repository.save(e);
	}

	public ModelAndView login(String name, String password, ModelAndView mv, HttpSession session) {
		Employee emp = repository.findByNameAndPassword(name, password);
		session.setAttribute("employee", emp);
		if (emp != null) {
			mv.setViewName("web");
		} else {
			mv.setViewName("login");
			mv.addObject("message", "Username or password incorrect!");
		}
		return mv;
	}

	public ModelAndView register(Employee e, ModelAndView mv) {
		if (!(e.getPassword().isBlank() && e.getRpassword().isBlank())) {
			mv.setViewName("register");
			List<Employee> list = repository.findByName(e.getName());
			if (list.isEmpty()) {
				repository.save(e);
				mv.addObject("success", "Successful!");
			} else {
				mv.addObject("fail", "User already exists!");
			}
		} else {
			mv.addObject("fail", "Passwords do not match!");
		}
		return mv;
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public ModelAndView list(ModelAndView mv) {
		List<Employee> list = repository.findAll();
		mv.setViewName("list");
		mv.addObject("list", list);
		return mv;
	}

	public ModelAndView update(Integer id, ModelAndView mv, HttpSession session) {
		if (id == null) {
			Employee emp = (Employee) session.getAttribute("employee");
			id = emp.getId();
		}
		Employee employee = repository.findById(id).get();
		mv.setViewName("update");
		mv.addObject("employee", employee);
		return mv;
	}

}
