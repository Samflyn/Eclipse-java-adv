package assignment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThirdOne {
	public static void main(String[] args) throws SQLException, IOException {
		File fs = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			PreparedStatement ps = con.prepareStatement("insert into sam values(?,?,?,?)");
			fs = new File(args[0]);
			fis = new FileInputStream(fs);
			dis = new DataInputStream(fis);
			String line = dis.readLine();
			while (line != null && !line.isEmpty()) {
				String[] split = line.split(",");
				int id = Integer.parseInt(split[0]);
				String empname = split[1];
				Double sal = Double.parseDouble(split[2]);
				int deptno = Integer.parseInt(split[3]);
				ps.setInt(1, id);
				ps.setString(2, empname);
				ps.setDouble(3, sal);
				ps.setInt(4, deptno);
				ps.addBatch();
				line = dis.readLine();
			}
			int[] batch = ps.executeBatch();
			System.out.println(batch.length + " records inserted");
			dis.close();
			fis.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
			if (dis != null) {
				dis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}
}
