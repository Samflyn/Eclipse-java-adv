package preparedstatemenets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPrepStatement {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			PreparedStatement ps = con.prepareStatement("insert into sam values(?,?,?,?)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			do {
				System.out.println("enter emp no");
				int id = Integer.parseInt(br.readLine());
				System.out.println("enter emp name");
				String str = br.readLine();
				System.out.println("enter emp sal");
				Double sal = Double.parseDouble(br.readLine());
				System.out.println("enter emp dept");
				String dept = br.readLine();
				ps.setInt(1, id);
				ps.setString(2, str);
				ps.setDouble(3, sal);
				ps.setString(4, dept);
				int i = ps.executeUpdate();
				System.out.println(i + "records inserted");
				System.out.println("continue y/n");
				String ch = br.readLine();
				if (ch.startsWith("n")) {
					break;
				}
			} while (true);
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
