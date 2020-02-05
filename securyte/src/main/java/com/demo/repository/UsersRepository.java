package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.bean.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByName(String name);

}
