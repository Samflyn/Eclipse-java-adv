package mapping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {
	public static void main(String[] args) {
		Laptop lp = new Laptop();
		lp.setLid(1060);
		lp.setLname("msi");
		Employee emp = new Employee();
		emp.setEmpno(1);
		emp.setEmpname("sam");
		emp.setDeptno(10);
		emp.getLaptop().add(lp);
		Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class)
				.addAnnotatedClass(Laptop.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		session.save(lp);
		session.save(emp);
		t.commit();
		session.close();
	}
}
