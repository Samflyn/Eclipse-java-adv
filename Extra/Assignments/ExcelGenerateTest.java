package com.stech.mnrao.excel;

import java.sql.Statement;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class ExcelGenerateTest {
	
	public static void main(String[] args) {
		

		String  destExcelFilePath = args[0];
				
		Connection con=null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from employee");
			StringBuffer buffer = new StringBuffer();
			while(rs.next())
			{
				String record = rs.getInt(1)+":"+
							 	rs.getString(2)+":"+
							 	rs.getDouble(3)+":"+
							 	rs.getInt(4)+":"+
							 	rs.getString(5)+":"+
							 	rs.getInt(6);
				
				buffer.append(record+"\n");
				
				
			}
			
			String data = buffer.toString();
			
			String[] records = data.split("\n");
			
			ExelFileGenerator  generator = new  ExelFileGenerator();
			
			generator.generateExcelFile(records,destExcelFilePath );
					
			con.close();
			
			System.out.println("Successfully Excel File generated");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
