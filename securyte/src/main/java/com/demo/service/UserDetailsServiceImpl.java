package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.bean.Roles;
import com.demo.bean.Users;
import com.demo.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersRepository.findByName(username);
		if (users == null)
			throw new UsernameNotFoundException(username);

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Roles role : users.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return new org.springframework.security.core.userdetails.User(users.getName(), users.getPassword(),
				grantedAuthorities);
	}

}
