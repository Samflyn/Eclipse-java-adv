package assignment;

import java.sql.SQLException;

public class TenthOne {
	public static void main(String[] args) throws SQLException {
		OracleDataParser odp = new OracleDataParser();
		StringBuffer data = odp.getData();
		SQLdataInsert sqld = new SQLdataInsert();
		sqld.insertData(data);
		System.out.println("End of data transfer");
	}
}
