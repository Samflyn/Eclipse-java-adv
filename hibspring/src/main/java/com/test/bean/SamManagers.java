package com.test.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "samman")
public class SamManagers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@OneToMany(mappedBy = "SamEmployees")
	private int mid;
	private String name;
	private String password;
	private String dob;
	private String gender;
	private String role;
}