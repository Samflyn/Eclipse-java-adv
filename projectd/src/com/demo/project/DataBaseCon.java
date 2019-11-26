package com.demo.project;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class DataBaseCon extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Connection getConn() {
		Connection con = null;
		try {
			ServletContext context = getServletContext();
			String Driver = context.getInitParameter("dbDriver");
			String Host = context.getInitParameter("dbHost");
			String port = context.getInitParameter("dbport");
			String Uid = context.getInitParameter("dbUid");
			String password = context.getInitParameter("dbpassword");
			String sid = context.getInitParameter("dbsid");
			Class.forName(Driver);
			String url = "jdbc:oracle:thin:@" + Host + ":" + port + ":" + sid;
			con = DriverManager.getConnection(url, Uid, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}