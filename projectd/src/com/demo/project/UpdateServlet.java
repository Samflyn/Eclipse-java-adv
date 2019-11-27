package com.demo.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet {
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
		String date = req.getParameter("date");
		String gender = req.getParameter("gender");
		String dept = req.getParameter("dept");
		String role = req.getParameter("role");
		try {
			con = cont.getConnection(getServletContext());
			PreparedStatement ps = con
					.prepareStatement("update employees set password=?,date=?,gender=?,dept=?,role=? where name=?");
			ps.setString(6, name);
			ps.setString(1, password);
			ps.setString(2, date);
			ps.setString(3, gender);
			ps.setString(4, dept);
			ps.setString(5, role);
			con.commit();
			out.println("<h3 style=\"color: green;\">Sucessfully updated!</h3>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
