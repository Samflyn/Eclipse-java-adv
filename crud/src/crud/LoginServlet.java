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

public class LoginServlet extends HttpServlet {

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
			statement = connection.prepareStatement("SELECT COUNT(*) FROM SAILO1 WHERE USERID=? AND PASSWORD=?");
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
			String userID = req.getParameter("userID");
			String passwd = req.getParameter("password");
			statement.setString(1, userID);
			statement.setString(2, passwd);
			ResultSet executeQuery = statement.executeQuery();
			executeQuery.next();
			int count = executeQuery.getInt(1);
			if (count == 1) {
				HttpSession session = req.getSession();
				session.setAttribute("sid", userID);
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("DashboardServlet");
				requestDispatcher.forward(req, resp);
			} else {
				writer.println("<h2 style=" + "color:red;" + " >Username or Password invalid !</h2>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginform.html");
				requestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style="+"color: red;"+">Server Busy!</h1>");
		}
	}
}
