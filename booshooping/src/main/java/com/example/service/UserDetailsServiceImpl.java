package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bean.Customer;
import com.example.repository.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> optional = customerRepository.findByEmail(username);
		UserDetailsImpl userDetailsImpl;
		if (optional.isPresent()) {
			userDetailsImpl = new UserDetailsImpl(optional.get());
		} else {
			userDetailsImpl = new UserDetailsImpl(new Customer());
		}
		return userDetailsImpl;
	}

}