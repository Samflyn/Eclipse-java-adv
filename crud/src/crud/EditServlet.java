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

public class EditServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement verify = null;
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
			verify = connection.prepareStatement("SELECT * FROM SAILO1 WHERE USERID=? ");
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
			HttpSession session = req.getSession(false);
			String userName = (String) session.getAttribute("sid");
			verify.setString(1, userName);
			ResultSet result = verify.executeQuery();
			result.next();
			String userID = result.getString(1);
			String passwd = result.getString(2);
			String rpasswd = null;
			String firstName = result.getString(3);
			String lastName = result.getString(4);
			String date = result.getString(5);
			String gender = result.getString(6);
			writer.println("<title>Edit Profile</title>");
			writer.println("<h1>Edit Profile</h1><hr>");
			writer.println("<input type=\"submit\" onclick=\"window.location.href='./logout';\" value=\"logout\">");
			writer.println(
					"User ID:<input type=\"text\" name=\"userID\" value=\"" + userID + "\" disabled>" + "<br><br>");
			writer.println("Password: <input type=\"password\" name=\"passwd\" value=\"" + passwd + "\">" + "<br><br>");
			writer.println("Re-type Password: <input type=\"password\" name=\"rpasswd\" value=\"" + rpasswd + "\">");
			writer.println("First Name: <input type=\"text\" name=\"fname\" value=\"" + firstName + "\">");
			writer.println("Last Name: <input type=\"text\" name=\"lname\" value=\"" + lastName + "\">");
			writer.println("Date Of Birth: <input type=\"date\" name=\"dob\" value=\"" + date + "\">");
			writer.println("Gender: <input type=\"text\" name=\"gender\" value=\"" + gender + "\">");
			writer.println("<input type=\"submit\" value=\"submit\">");
			String passwd1 = req.getParameter("passwd");
			String rpasswd1 = req.getParameter("rpasswd");
			String firstName1 = req.getParameter("fname");
			String lastName1 = req.getParameter("lname");
			String date1 = req.getParameter("dob");
			String gender1 = req.getParameter("gender");
			if (!passwd1.equals(rpasswd1)) {
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
			writer.println("<h3 style=\"color: green;\">Sucessfully registered!</h3>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("registerform.html");
			requestDispatcher.include(req, resp);
			writer.println("<input type=\"reset\" onclick=\"window.location.href='./profile'\" value=\"cancel\">");
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style=" + "color: red;" + ">Server Busy!</h1>");
		}
	}

}
