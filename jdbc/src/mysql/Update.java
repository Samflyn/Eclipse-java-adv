package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb:root:admin123");
			Statement st = con.createStatement();
			String sqlQuery = "update emp set salary=20000 where empno=1001";
			int rs = st.executeUpdate(sqlQuery);
			if (rs > 1) {
				System.out.println("Updated");
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
