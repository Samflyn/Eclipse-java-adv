package com.stech.mnrao.jdbc;

import java.sql.Statement;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DatabaseInformation {
	
	public static void main(String[] args) {
		
		Connection con =null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			DatabaseMetaData dbmd = con.getMetaData();
			
			System.out.println("DB product : "+dbmd.getDatabaseProductName());
			
			System.out.println("DB Version : "+dbmd.getDatabaseProductVersion());
			
			System.out.println("DB Major Version :"+dbmd.getDatabaseMajorVersion());
			System.out.println("DB Minor Version : "+dbmd.getDatabaseMinorVersion());
			
			System.out.println("Driver name : "+dbmd.getDriverName());
			
			System.out.println("Driver Version : "+dbmd.getDriverVersion());
			System.out.println("User Id : "+dbmd.getUserName());
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
