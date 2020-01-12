package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bean.Cart;
import com.example.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("from Customer c where c.name=?1 and c.password=?2")
	Optional<Customer> findByNameAndPassword(String name, String password);

	Optional<Customer> findByName(String name);

	@Modifying
	@Query("update Customer c set c.cart=?1 where c.id=?2")
	void updateCart(List<Cart> updateCart, int id);

}
