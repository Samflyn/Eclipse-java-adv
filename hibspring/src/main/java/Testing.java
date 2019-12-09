import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;

public class Testing {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from SamEmployees");
		List list = query.list();
		Iterator itr = list.iterator();
		SamEmployees s = (SamEmployees) itr.next();
		System.out.println(s.getName());
		session.close();
	}
}
