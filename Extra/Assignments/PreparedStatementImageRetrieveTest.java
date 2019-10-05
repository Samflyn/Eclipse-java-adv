package com.stech.mnrao.jdbc;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import oracle.sql.BLOB;

public class PreparedStatementImageRetrieveTest {
	
	public static void main(String[] args) {
		
		
		Connection con=null;
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","tiger");
			
			PreparedStatement pst = con.prepareStatement("select * from employee");	
				
				ResultSet rs =  pst.executeQuery();	
				
				rs.next();
				
				int eno = rs.getInt(1);
				
				Blob  blob = rs.getBlob(7);
			
				InputStream is = blob.getBinaryStream();
				
				FileOutputStream fos = new FileOutputStream(args[0]);
				
				int i = is.read();
				
				while(i!=-1)
				{
					fos.write(i);
					i = is.read();
				}
				fos.close();
				con.close();
			
			System.out.println("Successfully Retrived from database");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
