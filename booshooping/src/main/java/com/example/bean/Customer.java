package com.example.bean;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String password;
	@Transient
	private String rpassword;
	@ElementCollection(targetClass = String.class)
	private List<String> address;
	@ElementCollection(targetClass = String.class)
	private List<String> transaction;
	@ElementCollection(targetClass = Products.class, fetch = FetchType.EAGER)
	private List<Products> cart;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public List<String> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<String> transaction) {
		this.transaction = transaction;
	}

	public List<Products> getCart() {
		return cart;
	}

	public void setCart(List<Products> cart) {
		this.cart = cart;
	}

}
