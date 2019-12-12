package com.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;
import com.test.conn.HibCon;

public class RegisterService {

	public static String register(SamEmployees s) {
		String register = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(s);
			transaction.commit();
			register = "success";
		} catch (Exception e) {
			e.printStackTrace();
			register = "failed";
		}
		return register;
	}

	public static String delete(int i) {
		String result = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("delete from SamEmployees where id= :id");
			query.setParameter("id", i);
			int j = query.executeUpdate();
			if (j > 0) {
				result = "Sucessfully deleted!";
			} else {
				result = "Failed to delete";
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = "Server busy!";
		}
		return result;
	}
}
