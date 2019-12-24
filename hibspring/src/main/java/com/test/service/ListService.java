package com.test.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;
import com.test.conn.HibCon;

public class ListService {

	public static List getList() {
		List list = null;
		try {
			SessionFactory sf = HibCon.getSessionFactory();
			Session s = sf.openSession();
			Query query = s.createQuery("from SamEmployees");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static SamEmployees getResult(String name) {
		SamEmployees se = null;
		try {
			SessionFactory sf = HibCon.getSessionFactory();
			Session s = sf.openSession();
			Query query = s.createQuery("from SamEmployees s where s.name = :name");
			query.setParameter("name", name);
			se = (SamEmployees) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return se;
	}
}
