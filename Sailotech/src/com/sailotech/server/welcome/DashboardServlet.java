package com.sailotech.server.welcome;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {
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
		try {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			RequestDispatcher linkRequestDispatcher = req.getRequestDispatcher("Dashboard.html");
			linkRequestDispatcher.include(req, resp);
			HttpSession session = req.getSession(false);
			if (session != null) {
				String userID = (String) session.getAttribute("sid");
				out.print("Hello, " + userID + " Welcome to Dashboard");
			} else {
				out.print("Please login first");
				RequestDispatcher loginRequestDispatcher = req.getRequestDispatcher("Login.html");
				loginRequestDispatcher.include(req, resp);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
