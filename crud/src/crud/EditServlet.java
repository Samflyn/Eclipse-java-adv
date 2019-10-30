package crud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
			String firstName = result.getString(3);
			String lastName = result.getString(4);
			String date = result.getString(5);
			String gender = result.getString(6);
			writer.println("<body>");
			writer.println("<form action=\"./update\" method=\"post\">");
			writer.println("<title>Edit Profile</title>");
			writer.println("<h1>Edit Profile</h1><hr>");
			writer.println("<input type=\"submit\" onclick=\"window.location.href='./logout';\" value=\"logout\">");
			writer.println(
					"User ID:<input type=\"text\" name=\"userID\" value=\"" + userID + "\" disabled>" + "<br><br>");
			writer.println("Password: <input type=\"password\" name=\"passwd\" value=\"" + passwd + "\">" + "<br><br>");
			writer.println("Re-type Password: <input type=\"password\" name=\"rpasswd\" value=\"" + passwd + "\">"
					+ "<br><br>");
			writer.println("First Name: <input type=\"text\" name=\"fname\" value=\"" + firstName + "\">" + "<br><br>");
			writer.println("Last Name: <input type=\"text\" name=\"lname\" value=\"" + lastName + "\">" + "<br><br>");
			writer.println("Date Of Birth: <input type=\"date\" name=\"dob\" value=\"" + date + "\">" + "<br><br>");
			writer.println("Gender: <input type=\"text\" name=\"gender\" value=\"" + gender + "\">" + "<br><br>");
			writer.println("<input type=\"submit\" value=\"submit\">");
			writer.println("<input type=\"reset\" onclick=\"window.location.href='./profile'\" value=\"cancel\">");
			writer.println("</form>");
			writer.println("</body>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style=" + "color: red;" + ">Server Busy!</h1>");
		}
	}

}
