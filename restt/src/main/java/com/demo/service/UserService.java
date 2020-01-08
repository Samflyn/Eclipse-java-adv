package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.User;
import com.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository repo;

	public void register(User user) {
		repo.save(user);
	}

	public List<User> getAll() {
		List<User> list = repo.findAll();
		return list;
	}

	public User getUser(Integer id) {
		User user = null;
		if (repo.existsById(id)) {
			user = repo.findById(id).get();
		}
		return user;
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
