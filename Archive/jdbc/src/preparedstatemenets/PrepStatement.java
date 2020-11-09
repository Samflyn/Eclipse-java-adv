package preparedstatemenets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrepStatement {
	public static void main(String[] args) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			String query = "insert into emp values (?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setInt(1, 10001);
			ps.setString(2, "sam");
			ps.setString(3, "CEO");
			ps.setInt(4, 20);
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
