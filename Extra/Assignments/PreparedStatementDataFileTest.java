package com.stech.mnrao.jdbc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PreparedStatementDataFileTest {
	
	public static void main(String[] args) {
		
		File  filePath = new File(args[0]);
		
		FileInputStream fis = null;
		DataInputStream dis=null;
		Connection con=null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?,?,?,?)");
			
			fis = new FileInputStream(filePath);
			dis = new DataInputStream(fis);
			
			String record = dis.readLine();
			
			while(record!=null && !record.isEmpty())
			{
				String[] field = record.split("#");
				
				pst.setInt(1, Integer.parseInt(field[0]));
				pst.setString(2, field[1]);
				pst.setDouble(3, Double.parseDouble(field[2]));
				pst.setInt(4, Integer.parseInt(field[3]));
				pst.setString(5, field[4]);
				pst.setInt(6, Integer.parseInt(field[5]));
				pst.executeUpdate();
				record = dis.readLine();
			}
			
			dis.close();
			fis.close();			
			con.close();
			
			System.out.println("Successfully loaded into database");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
