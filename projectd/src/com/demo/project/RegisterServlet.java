package com.demo.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement ver = null;
	ConnectionGen cont = new ConnectionGen();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String rpassword = req.getParameter("rpassword");
		String date = req.getParameter("date");
		String gender = req.getParameter("gender");
		String dept = req.getParameter("dept");
		String role = req.getParameter("role");
		if (!password.equals(rpassword)) {
			out.println("<h3 style=\"color: red;\">Password Mis-matched</h3>");
			return;
		}
		if (name != null && password != null && date != null && gender != null && dept != null && role != null) {
			try {
				con = cont.getConnection(getServletContext());
				PreparedStatement ps = con.prepareStatement("INSERT INTO EMPLOYEES VALUES (?,?,?,?,?,?)");
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, date);
				ps.setString(4, gender);
				ps.setString(5, dept);
				ps.setString(6, role);
				ver = con.prepareStatement("SELECT COUNT(*) FROM EMPLOYEES WHERE NAME=?");
				ver.setString(1, name);
				ResultSet executeQuery = ver.executeQuery();
				executeQuery.next();
				int count = executeQuery.getInt(1);
				if (count == 1) {
					out.println("<h3 style=\"color: red;\">Name already exists!</h3>");
				} else {
					ps.executeUpdate();
					con.commit();
					out.println("<h3 style=\"color: green;\">Sucessfully registered!</h3>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			out.println("<h3 style=\"color: red;\">Please fill all details!</h3>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
