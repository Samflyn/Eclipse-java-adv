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

public class DeleteServlet extends HttpServlet {
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
		try {
			con = cont.getConnection(getServletContext());
			PreparedStatement ps = con.prepareStatement("delete from employees where name=?");
			ps.setString(1, name);
			int update = ps.executeUpdate();
			if (update > 1) {
				out.println("<h3 style=\"color: green;\">Sucessfully deleted!</h3>");
			} else {
				out.println("<h3 style=\"color: red;\">Failed to delete!</h3>");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
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
