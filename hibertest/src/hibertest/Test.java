package hibertest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.configure("Employee.cfg.xml");
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		Transaction ts = session.beginTransaction();
		Employee emp = new Employee();
		emp.setEmpnum(1002);
		emp.setEname("sam");
		emp.setSalary(1001);
		emp.setDept(10);
		session.persist(emp);
		ts.commit();
		session.close();
		System.out.println("done");
	}
}
