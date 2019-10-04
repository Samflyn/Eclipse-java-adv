package orclData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScrolableResultSet {
	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba","apps","apps");
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "Select * from emp";
			ResultSet rs = st.executeQuery(query);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
