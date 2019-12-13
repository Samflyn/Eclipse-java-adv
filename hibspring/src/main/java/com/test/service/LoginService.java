package com.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;
import com.test.conn.HibCon;

public class LoginService {

	public static String authenticate(String name, String password) {
		String role = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from SamEmployees e where e.name = :name and e.password = :password");
			query.setParameter("name", name);
			query.setParameter("password", password);
			SamEmployees s = (SamEmployees) query.uniqueResult();
			if (s != null) {
				role = s.getRole();
			} else {
				return role = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
			role = "error";
		}
		return role;
	}
}
