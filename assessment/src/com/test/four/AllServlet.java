package com.test.four;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connnection = null;
	Statement statement = null;

	@Override
	public void init() throws ServletException {
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
			connnection = DriverManager.getConnection(url, Uid, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		try {
			Statement st = connnection.createStatement();
			ResultSet rs = st.executeQuery("select * from sailo2");
			out.write("<html>");
			out.write("<body>");
			out.write("<h2 style=\"text-align: center;\">All User List</h2>");
			out.write("<table style=\"width:100%\">\n" + "        <tr>\n" + "            <th>Firstname</th>\n"
					+ "            <th>Lastname</th>\n" + "            <th>Date Of Birth</th>\n"
					+ "            <th>Gender</th>\n" + "            <th>Department</th>\n" + "        </tr>");
			while (rs.next()) {
				out.write("<tr>");
				out.write("<tr>" + rs.getString(1) + "<tr>");
				out.write("<tr>" + rs.getString(2) + "<tr>");
				out.write("<tr>" + rs.getString(3) + "<tr>");
				out.write("<tr>" + rs.getString(4) + "<tr>");
				out.write("<tr>" + rs.getString(5) + "<tr>");
				out.write("<tr>");
			}
			out.write("</table>");
			out.write("</body>");
			out.write("</html>");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 style=\"color: red;\">Server Busy!</h1>");
			try {
				connnection.rollback();
			} catch (SQLException p) {
				p.printStackTrace();
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void destroy() {
		if (connnection != null) {
			try {
				connnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
