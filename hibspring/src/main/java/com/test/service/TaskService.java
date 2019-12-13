package com.test.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;
import com.test.conn.HibCon;

public class TaskService {

	public static String setStatus(String name, String status) {
		String result = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("update SamEmployees set status = :status where name = :name");
			query.setParameter("status", status);
			query.setParameter("name", name);
			int i = query.executeUpdate();
			transaction.commit();
			if (i > 0) {
				result = "success";
			} else {
				result = "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Server Busy";
		}
		return result;
	}

	public static String setTask(int id, String task, String assignedby) {
		String result = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery(
					"update SamEmployees set task = :task , assignedby = :assignedby , status =:status where id = :id");
			query.setParameter("task", task);
			query.setParameter("assignedby", assignedby);
			query.setParameter("status", null);
			query.setParameter("id", id);
			int i = query.executeUpdate();
			transaction.commit();
			if (i > 0) {
				result = "success";
			} else {
				result = "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Server Busy";
		}
		return result;
	}

	public static List getStatus(String name) {
		List list = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from SamEmployees where assignedby = :name");
			query.setParameter("name", name);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String removeTask(String name) {
		String result = null;
		try {
			SessionFactory sessionFactory = HibCon.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery(
					"update SamEmployees s set s.task= :task , s.assignedby = :assignedby , status =:status where s.name= :name");
			query.setParameter("task", null);
			query.setParameter("assignedby", null);
			query.setParameter("status", null);
			query.setParameter("name", name);
			int i = query.executeUpdate();
			transaction.commit();
			if (i > 0) {
				result = "Task removed";
			} else {
				result = "Task failed to remove";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}