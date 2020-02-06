package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.User;
import com.demo.repository.UserRepository;

@Service
public class PublicService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ModelAndView register(User user, ModelAndView mav) {
		user.setId(1);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		mav.setViewName("register");
		return mav;
	}

}
