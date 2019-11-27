package com.demo.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet {
	Connection con = null;
	PreparedStatement ps = null;
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
		String date = req.getParameter("date");
		String gender = req.getParameter("gender");
		String dept = req.getParameter("dept");
		String role = req.getParameter("role");
		System.out.println(name + " " + password + " " + date + " " + gender + " " + dept + " " + role);
		try {
			con = cont.getConnection(getServletContext());
			con.setAutoCommit(false);
			int count = 0;
			if (name != null && password != null && date != null && gender != null) {
				if (dept != null && role != null) {
					System.out.println("if");
					ps = con.prepareStatement(
							"update employees set password=?,date=?,gender=?,dept=?,role=? where name=?");
					ps.setString(1, password);
					ps.setString(2, date);
					ps.setString(3, gender);
					ps.setString(4, dept);
					ps.setString(5, role);
					ps.setString(6, name);
					count = ps.executeUpdate();
				} else {
					System.out.println("else");
					ps = con.prepareStatement("UPDATE EMPLOYEES SET PASSWORD=?, DATE=?, GENDER=? WHERE NAME=?");
					ps.setString(1, password);
					ps.setString(2, date);
					ps.setString(3, gender);
					ps.setString(4, name);
					count = ps.executeUpdate();
				}
				con.commit();
				if (count > 1) {
					out.println("<h3 style=\"color: green;\">Sucessfully updated!</h3>");
				} else {
					out.println("<h3 style=\"color: red;\">Failed to update!</h3>");
				}
				con.close();
			} else {
				out.println("<h3 style=\"color: red;\">Fill all details!</h3>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
