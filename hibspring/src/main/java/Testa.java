import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.test.bean.SamEmployees;

public class Testa {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Query query = session.createQuery("select count(*) from SamEmployees s where s.name = :name and s.password = :password");
		query.setParameter("name", "root");
		query.setParameter("password", "root");
		Long i = (Long)query.uniqueResult();
		System.out.println(i);
	}
}
