package com.demo.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	Connection con;
	ConnectionGen cont = new ConnectionGen();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try {
			con = cont.getConnection(getServletContext());
			PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM EMPLOYEES WHERE NAME=? AND PASSWORD=?");
			String name = req.getParameter("name");
			String passwd = req.getParameter("password");
			ps.setString(1, name);
			ps.setString(2, passwd);
			ResultSet executeQuery = ps.executeQuery();
			executeQuery.next();
			int count = executeQuery.getInt(1);
			if (count == 1) {
				PreparedStatement prole = con.prepareStatement("SELECT * FROM EMPLOYEES WHERE NAME=? AND PASSWORD=?");
				prole.setString(1, name);
				prole.setString(2, passwd);
				ResultSet rrole = prole.executeQuery();
				rrole.next();
				String role = rrole.getString(6);
				HttpSession session = req.getSession();
				session.setAttribute("name", name);
				session.setAttribute("role", role);
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("web.jsp");
				requestDispatcher.forward(req, resp);
			} else {
				out.println("<h2 style=" + "color:red;" + " >Username or Password invalid !</h2>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("LoginForm.html");
				requestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 style=\"color: red;\">Server Busy!</h1>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
