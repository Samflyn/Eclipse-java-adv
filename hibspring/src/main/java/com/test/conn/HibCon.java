package com.test.conn;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibCon {
	private static SessionFactory sessionFactory = null;

	public synchronized static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
				return sessionFactory;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
