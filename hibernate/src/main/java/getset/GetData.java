package getset;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class GetData {
	public static void main(String[] args) {
		Employee emp = new Employee();
		Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		emp = (Employee) session.get(Employee.class, 1001);
		t.commit();
		session.close();
		System.out.println(emp);
	}
}
