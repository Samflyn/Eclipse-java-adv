package connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {

		Connection con = null;
		DataBaseProp pro = new DataBaseProp();
		String query = "select * from emp";
		con = pro.getConnection(args[0]);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getInt(1));
		}
		con.close();
	}
}
