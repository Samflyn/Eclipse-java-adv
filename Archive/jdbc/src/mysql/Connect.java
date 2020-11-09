package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
			Statement st = con.createStatement();
			String sqlQuery = "select * from emp";
			ResultSet rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				System.out.println();
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
