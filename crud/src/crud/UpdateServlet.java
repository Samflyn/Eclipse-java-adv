package crud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement update = null;
	
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
			update = connection.prepareStatement(
					"UPDATE SAILO1 SET PASSWORD=?,FIRSTNAME=?,LASTNAME=?,DATE=?,GENDER=? WHERE USERID=?");
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
			String userName = req.getParameter("userName");
			String passwd = req.getParameter("passwd");
			String rpasswd = req.getParameter("rpasswd");
			String firstName = req.getParameter("fname");
			String lastName = req.getParameter("lname");
			String date = req.getParameter("dob");
			String gender = req.getParameter("gender");
			if (!passwd.equals(rpasswd)) {
				writer.println("<h3 style=\"color: red;\">Password Mis-matched</h3>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("EditServlet");
				requestDispatcher.include(req, resp);
				return;
			}
			update.setString(1, passwd);
			update.setString(2, firstName);
			update.setString(3, lastName);
			update.setString(4, date);
			update.setString(5, gender);
			update.setString(6, userName);
			update.executeUpdate();
			writer.println("<h3 style=\"color: green;\">Sucessfully updated!</h3>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("EditServlet");
			requestDispatcher.include(req, resp);
		} catch(Exception e) {
			e.printStackTrace();
			writer.println("<h1 style=" + "color: red;" + ">Server Busy!</h1>");
		}
	}
}
