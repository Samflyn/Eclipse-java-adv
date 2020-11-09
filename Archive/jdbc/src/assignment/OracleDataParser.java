package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDataParser {
	StringBuffer sb = null;

	public StringBuffer getData() throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523", "apps", "apps");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from sam");
			while (rs.next()) {
				sb.append(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getInt(3) + ":" + rs.getInt(4));
				sb.append('\n');
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return sb;
	}

}
