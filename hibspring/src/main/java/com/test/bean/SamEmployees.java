package com.test.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "samemp")
public class SamEmployees {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eid;
	private String name;
	private String password;
	private String dob;
	private String gender;
	private String role;
	@ManyToOne
	private int mid;
	private String task;
	private String status;
}