package connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		DataBaseProp pro = new DataBaseProp();
		ResultSet prop = pro.DataBaseProp(args[0]);
		while(prop.next()) {
			System.out.println(prop.getInt(1));
		}
	}
}
