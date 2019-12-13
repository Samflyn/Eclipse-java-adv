import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;

public class Testa {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("update SamEmployees s set s.task= :task where s.id=36").setParameter("task",
				null);
		int i = query.executeUpdate();
		System.out.println(i);
		transaction.commit();
	}
}
