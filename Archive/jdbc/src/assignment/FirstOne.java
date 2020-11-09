package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class FirstOne {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		ArrayList<String> as = new ArrayList<String>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			Statement st = con.createStatement();
			String str = "select * from sam";
			ResultSet rs = st.executeQuery(str);
			while (rs.next()) {
				String ss = (rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getInt(4)).toString();
				as.add(ss);
			}
			con.close();
			Iterator<String> itr = as.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
