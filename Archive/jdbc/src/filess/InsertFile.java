package filess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertFile {
	public static void main(String[] args) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			String query = "insert into sam values (?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setInt(1, 1001);
			ps.setString(2, "sam");
			ps.setInt(3, 1001);
			FileInputStream fis = new FileInputStream(args[0]);
			FileReader fr = new FileReader(args[1]);
			File f = new File(args[1]);
			ps.setBinaryStream(4, fis, fis.available());
			ps.setCharacterStream(5, fr, f.length());
			int result = ps.executeUpdate();
			if (result != 0) {
				System.out.println("updated");
			} else {
				System.out.println("failed");
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
