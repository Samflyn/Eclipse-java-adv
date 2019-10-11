package assignment;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FifthOne {
	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from sam");
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				String ss = (rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getInt(3) + ":" + rs.getInt(4));
				sb.append(ss+'\n');
			}
			String string = sb.toString();
			String[] data = string.split("\n");
			File fi = new File(args[0]);
			FileInputStream fis = new FileInputStream(fi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
}
