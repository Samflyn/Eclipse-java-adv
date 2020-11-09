package connection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseProp {
	ResultSet rs = null;

	public Connection getConnection(String src) throws IOException, SQLException, FileNotFoundException {
		Connection con = null;
		String url = null;
		Properties prop = new Properties();
		try {
			FileReader file = new FileReader(src);
			prop.load(file);
			String dbProduct = prop.getProperty("dbProduct");
			String dbDriver = prop.getProperty("dbDriver");
			String dbHost = prop.getProperty("dbHost");
			String dbPort = prop.getProperty("dbPort");
			String dbUid = prop.getProperty("dbUid");
			String dbPasswd = prop.getProperty("dbPasswd");
			String dbSid = prop.getProperty("dbSid");
			String dbName = prop.getProperty("dbName");
			if (dbProduct.equalsIgnoreCase("oracle")) {
				url = "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":" + dbSid;
			} else if (dbProduct.equalsIgnoreCase("mysql")) {
				url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
			}
			Class.forName(dbDriver);
			con = DriverManager.getConnection(url, dbUid, dbPasswd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
