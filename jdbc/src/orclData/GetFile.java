package orclData;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetFile {
	public static void main(String[] args) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1523:sailodba", "apps", "apps");
			String query = "select * from sam";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			Blob blob = rs.getBlob(4);
			InputStream is = blob.getBinaryStream();
			int i = is.read();
			FileOutputStream fw = new FileOutputStream(args[0]);
			while (i != -1) {
				fw.write(i);
				i = is.read();
			}
			fw.flush();
			fw.close();
			Clob clob = rs.getClob(5);
			Reader cs = clob.getCharacterStream();
			int read = cs.read();
			FileWriter text = new FileWriter(args[1]);
			while (read != -1) {
				text.write(read);
				read = cs.read();
			}
			text.flush();
			text.close();
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
}
