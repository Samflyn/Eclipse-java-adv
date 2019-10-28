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
			HttpSession session = req.getSession(false);
			if (session != null) {
				String userID = (String) session.getAttribute("sid");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("Profile.html");
				requestDispatcher.forward(req, resp);
			} else {
				out.println("<h1>Hello</h1>");
				RequestDispatcher loginRequestDispatcher = req.getRequestDispatcher("Login.html");
				loginRequestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}