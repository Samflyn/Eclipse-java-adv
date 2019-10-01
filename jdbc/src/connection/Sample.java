package connection;
import java.sql.Connection;
import java.sql.DriverManager;

public class Sample {
	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.189:1521:apps:apps");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
