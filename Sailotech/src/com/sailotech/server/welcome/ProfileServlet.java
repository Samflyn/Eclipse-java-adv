package com.sailotech.server.welcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection connection = null;
		PreparedStatement statement = null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
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
			connection = DriverManager.getConnection(url, Uid, password);
			statement = connection.prepareStatement("SELECT * FROM SAILO1 WHERE USERID=? ");
			HttpSession session = req.getSession(false);
			if (session != null) {
				String userName = (String) session.getAttribute("sid");
				statement.setString(1, userName);
				ResultSet result = statement.executeQuery();
				result.next();
				String userID = result.getString(1);
				String firstName = result.getString(3);
				String lastName = result.getString(4);
				Date date = result.getDate(5);
				String gender = result.getString(6);
				out.println("<h1>Welcome, " + userID + "</h1><hr>");
				out.println("<input type=\"submit\" onclick=\"window.location.href='./logout';\" value=\"logout\">");
				out.println("<h3>User ID - " + userID + "</h3>");
				out.println("<h3>First Name - " + firstName + "</h3>");
				out.println("<h3>Last Name - " + lastName + "</h3>");
				out.println("<h3>Date Of Birth - " + date + "</h3>");
				out.println("<h3>Gender - " + gender + "</h3>");
			} else {
				out.println("<h1>Please Login First </h1><hr>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("LoginForm.html");
				requestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
