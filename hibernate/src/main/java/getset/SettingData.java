package getset;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class SettingData {
	public static void main(String[] args) {
		Employee emp = new Employee();
		emp.setEmpno(1001);		
		emp.setDeptno(10);
		emp.setSalary(1001);
		NameClass nc = new NameClass();
		nc.setFname("samf");
		nc.setLname("saml");
		nc.setMname("samm");
		emp.setEmpname(nc);
		Configuration con = new Configuration().configure().addAnnotatedClass(Employee.class);
		SessionFactory sf = con.buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		session.save(emp);
		t.commit();
		session.close();
	}
}
