package com.stech.mnrao.jdbc;


import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PropertiesFileTest {
	
	public static void main(String[] args) throws SQLException {
		
		Connection con =null;
		
		try
		{
			
			Properties dbProp = new Properties();
			
			File  databasePropertiesFilePath=new File(args[0]);
			
			FileReader  reader = new FileReader(databasePropertiesFilePath);
			
			dbProp.load(reader);
			
			reader.close();
			
			String product = dbProp.getProperty("dbProduct");
			
			String driver = dbProp.getProperty("dbDriver");
			
			String hostName = dbProp.getProperty("dbHost");
			
			String portNum = dbProp.getProperty("dbPort");
			
			String uid = dbProp.getProperty("dbUid");
			
			String pwd = dbProp.getProperty("dbPasswd");
			
			
			
			Class.forName(driver);
			
			String url = null;
			
			if(product.equalsIgnoreCase("oracle"))
			{
				System.out.println("reading from oracle database");
				String sid = dbProp.getProperty("dbSid");
				url = "jdbc:oracle:thin:@"+hostName+":"+portNum+":"+sid;
				
				
			}
			else if(product.equalsIgnoreCase("mysql"))
			{
				System.out.println("reading from mysql database");
				String dbName = dbProp.getProperty("dbName");
				url = "jdbc:mysql://"+hostName+":"+portNum+"/"+dbName;			
			}					  
			System.out.println("before Connected");
			
			con=DriverManager.getConnection(url, uid, pwd);
			
			System.out.println("Connected");
			Statement st = con.createStatement();
			
			String  sqlQuery= "select * from employee";
			
			ResultSet rs = st.executeQuery(sqlQuery);
			
			while(rs.next())
			{
				String record=rs.getInt(1)+","+rs.getString(2)+","+rs.getDouble(3)+","+rs.getInt(4)+","+rs.getString(5)+","+rs.getInt(6);
			}		
			
			
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if(con!=null)
			{
				con.close();
			}
		}
	}

}
