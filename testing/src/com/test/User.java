package com.test;

public class User {
	private int id;
	private String name;
	private String tee;

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

	public String getTee() {
		return tee;
	}

	public void setTee(String tee) {
		this.tee = tee;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", tee=" + tee + "]";
	}

}
