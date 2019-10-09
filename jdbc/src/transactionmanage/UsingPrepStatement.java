package transactionmanage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsingPrepStatement {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into sam values(?,?,?,?)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			do {
				System.out.println("enter emp no");
				int parseInt = Integer.parseInt(br.readLine());
				System.out.println("enter emp name");
				String readLine = br.readLine();
				System.out.println("enter emp sal");
				double parseDouble = Double.parseDouble(br.readLine());
				System.out.println("enter emp dept");
				String readLine2 = br.readLine();
				ps.setInt(1, parseInt);
				ps.setString(2, readLine);
				ps.setDouble(3, parseDouble);
				ps.setString(4, readLine2);
				ps.addBatch();
				System.out.println("want to enter more records y/n");
				String a = br.readLine();
				if (a.equals("n")) {
					break;
				}
			} while (true);
			int[] i = ps.executeBatch();
			System.out.println(i + " records inserted");
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
