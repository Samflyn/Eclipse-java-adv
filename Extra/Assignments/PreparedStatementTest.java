package com.stech.mnrao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PreparedStatementTest {
	
	public static void main(String[] args) {
		
		Connection con=null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?,?,?,?)");
			
			pst.setInt(1, 1001);
			pst.setString(2, "hello1");
			pst.setDouble(3, 5000);
			pst.setInt(4, 10);
			pst.setString(5, "male");
			pst.setInt(6, 25);
	
			int count = pst.executeUpdate();
			
			if(count>=0)
			{
				System.out.println(count+" records inserted");
			}
			else
			{
				System.out.println("failed to insert");
			}
			
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
