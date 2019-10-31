package ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;

public class UserCtrlServlet extends HttpServlet {

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
		int userId = Integer.parseInt(req.getParameter("userid"));
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String rpassword = req.getParameter("password1");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String city = req.getParameter("city");
		if(!password.equals(rpassword)) {
			writer.println("<h3>password mis-match</h3>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
			requestDispatcher.include(req, resp);
			return;
		}
		req.getSession(true);
		UserDAO userDAO = new UserDAO();
		userDAO.addUserDetails(userId, userName, password, email, phone, city);
		resp.sendRedirect("Success");
	}
}
