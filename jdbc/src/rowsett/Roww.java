package rowsett;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class Roww {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			JdbcRowSet rs = RowSetProvider.newFactory().createJdbcRowSet();
			rs.setUrl("jdbc:oracle:thin:@192.168.1.189:1523:sailodba");
			rs.setUsername("apps");
			rs.setPassword("apps");
			rs.setCommand("select * from sam");
			rs.execute();
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getDouble(3));
				System.out.println(rs.getString(4));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
