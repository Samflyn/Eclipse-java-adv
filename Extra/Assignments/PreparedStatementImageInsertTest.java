package com.stech.mnrao.jdbc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PreparedStatementImageInsertTest {
	
	public static void main(String[] args) {
		
		File  filePath = new File(args[0]);		
		
		Connection con=null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?,?,?,?, ?)");
			FileInputStream fis = new FileInputStream(args[0]);
				pst.setInt(1, 1001);
				pst.setString(2, "hello");
				pst.setDouble(3, 5000);
				pst.setInt(4, 10);
				pst.setString(5, "gender");
				pst.setInt(6, 24);
				pst.setBinaryStream(7, fis, fis.available());
				pst.executeUpdate();						
				con.close();
			
			System.out.println("Successfully inserted into database");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
