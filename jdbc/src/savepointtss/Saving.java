package savepointtss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class Saving {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		Savepoint sp1 = null;
		Savepoint sp2 = null;
		try {
			Class.forName("oracle.jdbc.driver.OracelDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			sp1 = con.setSavepoint("SP1");
			st.executeUpdate("insert into sam values(1000,'sammy',5000,'dev')");
			st.executeUpdate("insert into sam values(1001,'sammy',5000,'dev')");
			System.out.println("SP1 commited");
			sp2 = con.setSavepoint("SP2");
			st.executeUpdate("insert into sam values(1000,'sammy',5000,'dev')");
			st.executeUpdate("insert into sam values('sam','sammy',5000,'dev')");
			System.out.println("sp2 commited");
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback(sp1);
			System.out.println("rollback to sp2");
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
