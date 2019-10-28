package crud;

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
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		try {
			writer.println("<title>Dashboard</title>" + "<hr>" + "<br>");
			HttpSession session = req.getSession(false);
			String userID = (String) session.getAttribute("sid");
			if (!userID.isEmpty()) {
				writer.println("<h1 style=" + "text-align: center;" + "Welcome " + userID + "></h1>");
				writer.println("<h2 style=\"text-align: center;\">\n"
						+ "<input type=\"submit\" onclick=\"window.location.href='./profile';\" value=\"Profile\">");
				writer.println("<br>");
				writer.println(
						"<input type=\"submit\" onclick=\"window.location.href='./logout';\" value=\"Logout\"></h2>");
			} else {
				writer.println("<h2 style=" + "color:red;" + " >Please Login First!</h2>");
				RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginform.html");
				requestDispatcher.include(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style="+"color: red;"+">Server Busy!</h1>");
		}
	}

}
