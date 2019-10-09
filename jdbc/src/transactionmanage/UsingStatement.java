package transactionmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UsingStatement {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			st.addBatch("insert into sam values(1000,'abc',6000,'dev')");
			st.addBatch("insert into sam values(1000,'abc',6000,'dev')");
			st.addBatch("insert into sam values(1000,'abc',6000,'dev')");
			st.executeBatch();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
