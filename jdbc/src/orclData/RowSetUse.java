package orclData;

import java.sql.SQLException;

public class RowSetUse {
	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
