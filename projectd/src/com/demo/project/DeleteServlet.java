package com.demo.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
			if (update > 0) {
				out.println("Sucessfully deleted!");
			} else {
				out.println("Failed to delete!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
