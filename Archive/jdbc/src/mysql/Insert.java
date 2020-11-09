package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Insert {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
			Statement st = con.createStatement();
			String sqlQuery = "insert into emp vaules(1001,'sam',10000,20)";
			int rs = st.executeUpdate(sqlQuery);
			if (rs > 1) {
				System.out.println("Inserted");
			} else {
				System.out.println("failed");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
