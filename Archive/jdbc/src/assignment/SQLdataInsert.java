package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLdataInsert {

	public void insertData(StringBuffer data) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
			PreparedStatement ps = con.prepareStatement("insert into sam values(?,?,?,?)");
			String str = data.toString();
			String[] records = str.split("\n");
			for (String record : records) {
				String[] split = record.split(":");
				ps.setInt(1, Integer.parseInt(split[0]));
				ps.setString(2, split[1]);
				ps.setDouble(3, Double.parseDouble(split[2]));
				ps.setInt(4, Integer.parseInt(split[3]));
				ps.addBatch();
			}
			int[] batch = ps.executeBatch();
			System.out.println(batch.length + " records inserted into mysql");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

}
