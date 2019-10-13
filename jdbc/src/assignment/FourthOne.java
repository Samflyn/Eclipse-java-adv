package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FourthOne {
	public static void main(String[] args) throws SQLException {
		ExcelParser ep = new ExcelParser();
		String data = ep.parseExcelData(args[0]);
		String[] records = data.split("\n");
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			PreparedStatement ps = con.prepareStatement("insert into sam values(?,?,?,?)");
			for (String record : records) {
				String[] split = record.split(",");
				ps.setInt(1, Integer.parseInt(split[0]));
				ps.setString(2, split[1]);
				ps.setDouble(3, Double.parseDouble(split[2]));
				ps.setInt(4, Integer.parseInt(split[3]));
				ps.addBatch();
			}
			int[] batch = ps.executeBatch();
			System.out.println(batch.length + " records inserted");
			ps.close();
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
