package crud;

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
	Connection connection = null;
	PreparedStatement statement = null;

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
			connection = DriverManager.getConnection(url, Uid, password);
			statement = connection.prepareStatement("SELECT * FROM SAILO1 WHERE USERID=? ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
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
				writer.println("<title>Profile</title>");
				writer.println("<h1>Welcome, " + userID + "</h1><hr>");
				writer.println("<input type=\"submit\" onclick=\"window.location.href='./logout';\" value=\"logout\">");
				writer.println("<h3>User ID - " + userID + "</h3>");
				writer.println("<h3>First Name - " + firstName + "</h3>");
				writer.println("<h3>Last Name - " + lastName + "</h3>");
				writer.println("<h3>Date Of Birth - " + date + "</h3>");
				writer.println("<h3>Gender - " + gender + "</h3>");
				writer.println("<input type=\"submit\" onclick=\"window.location.href='./edit'\" value=\"Edit\">");
			} else {
				writer.println("<h1 style=\"color: red;\">Please login first!</h1>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginform.html");
				requestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style=" + "color: red;" + ">Server Busy!</h1>");
		}
	}

}
