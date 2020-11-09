package metadataa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseM {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			DatabaseMetaData metaData = con.getMetaData();
			System.out.println(metaData.getDatabaseProductName());
			System.out.println(metaData.getDatabaseProductVersion());
			System.out.println(metaData.getDatabaseMajorVersion());
			String[] table = { "TABLE" };
			ResultSet tables = metaData.getTables(null, null, null, table);
			while (tables.next()) {
				System.out.println(tables.getString(3));
			}
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
