package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("from Customer c where c.email=?1 and c.password=?2")
	Optional<Customer> findByEmailAndPassword(String email, String password);

	Optional<Customer> findByEmail(String email);

	@Modifying
	@Query("update Customer c set c.name=?2, c.password=?3, c.dob=?4, c.gender=?5 where c.id=?1")
	void updateCustomer(int id, String name, String password, String dob, String gender);

}