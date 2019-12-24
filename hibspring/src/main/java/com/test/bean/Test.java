package com.test.bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction ss = session.beginTransaction();
		SamEmployees se = new SamEmployees();
		se.setId(1);
		SamEmployees s = new SamEmployees();
		s.setName("root");
		s.setPassword("root");
		s.setDob("2019-11-21");
		s.setGender("Male");
		s.setRole("Admin");
		s.setManager(se);
		session.save(s);
		ss.commit();
		session.close();
		System.out.println("ok");
	}
}
