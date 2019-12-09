package com.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.test.conn.HibCon;

public class LoginService {

	public String authenticate(String name, String password) {
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query equery = session.createQuery("from SamEmployees e where e.name = :name and e.password = :password");
			equery.setParameter("name", name);
			equery.setParameter("password", password);
			int i = equery.executeUpdate();
			if (i > 0) {

			} else {
				Query mquery = session
						.createQuery("from SamManagers m where m.name = :name and m.password = :password");
				mquery.setParameter("name", name);
				mquery.setParameter("password", password);
				int j = mquery.executeUpdate();
				if (j > 0) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Server Busy";
		}
		return null;
	}
}
