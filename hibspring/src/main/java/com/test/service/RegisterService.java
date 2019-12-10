package com.test.service;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.bean.SamEmployees;
import com.test.conn.HibCon;

public class RegisterService {

	public static String register(SamEmployees s) {
		String register = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			int i = (Integer) session.save(s);
			System.out.println(i);
			transaction.commit();
			register = "success";
		} catch (Exception e) {
			e.printStackTrace();
			register = "failed";
		}
		return register;
	}
}
