package crud;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

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
			HttpSession session = req.getSession(false);
			session.invalidate();
			writer.print("<h3 style=\"color: green;\">You're logged out!</h3>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginform.html");
			requestDispatcher.include(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("<h1 style=" + "color: red;" + ">Server Busy!</h1>");
		}
	}

}
