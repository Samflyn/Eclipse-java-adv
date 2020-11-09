package assignment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SecondOne {
	public static void main(String[] args) throws SQLException, IOException {
		Connection con = null;
		File fs = null;
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			Statement st = con.createStatement();
			String str = "select * from sam";
			ResultSet rs = st.executeQuery(str);
			fs = new File(args[0]);
			fos = new FileOutputStream(fs);
			dos = new DataOutputStream(fos);
			while (rs.next()) {
				String ss = (rs.getInt(1) + "," + rs.getString(2) + "," + rs.getInt(3) + "," + rs.getInt(4)).toString();
				dos.writeBytes(ss + '\n');
			}
			dos.flush();
			dos.close();
			con.close();
			System.out.println("CSV Generated");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
			if (dos != null) {
				dos.close();
			}
		}
	}
}
