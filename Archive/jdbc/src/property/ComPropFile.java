package property;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ComPropFile {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		Properties prop = new Properties();
		try {
			FileReader file = new FileReader(args[0]);
			prop.load(file);
			String dbProduct = prop.getProperty("dbProduct");
			String dbDriver = prop.getProperty("dbDriver");
			String dbHost = prop.getProperty("dbHost");
			String dbPort = prop.getProperty("dbPort");
			String dbUid = prop.getProperty("dbUid");
			String dbPasswd = prop.getProperty("dbPasswd");
			String dbSid = prop.getProperty("dbSid");
			String dbName = prop.getProperty("dbName");
			Class.forName(dbDriver);
			String dburl = null;
			if (dbProduct.equalsIgnoreCase("oracle")) {
				dburl = "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":" + dbSid;
			} else if (dbProduct.equalsIgnoreCase("mysql")) {
				dburl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
			}
			con = DriverManager.getConnection(dburl, dbUid, dbPasswd);
			Statement st = con.createStatement();
			String query = "select * from emp";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				System.out.println();
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
