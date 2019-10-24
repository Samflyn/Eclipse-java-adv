package assignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{

	Connection con=null;
	PreparedStatement pst=null;


@Override
public void init() throws ServletException {
		try
		{		ServletContext context = getServletContext();
			String Driver = context.getInitParameter("dbDriver");
			String Host = context.getInitParameter("dbHost");
			String port = context.getInitParameter("dbport");
			String Uid = context.getInitParameter("dbUid");
			String password = context.getInitParameter("dbpassword");
			String sid = context.getInitParameter("dbsid");
			
			
			Class.forName(Driver);
			String url="jdbc:oracle:thin:@"+Host+":"+port+":"+sid;
			con=DriverManager.getConnection(url,Uid,password);
			con.setAutoCommit(false);
			pst=con.prepareStatement("select count(*) from vvemp where USERNAME=? and PASSWORD=?");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		try
		{
			String Uname = req.getParameter("uid");
			String Password = req.getParameter("pwd");
			
		
			pst.setString(1,Uname );
			pst.setString(2,Password );
		
			ResultSet executeQuery = pst.executeQuery();
			 executeQuery.next();
			 int count = executeQuery.getInt(1);
			if(count==1)
			{
			    RequestDispatcher requestDispatcher = req.getRequestDispatcher("Profile.html");
			    requestDispatcher.forward(req, resp);
			    HttpSession session = req.getSession();
			    session.setAttribute("uid",Uname );
			}
			else
			{
			out.println("<h2 style="+"color:red;"+" >Username or Password invalid !</h2>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("LoginForm.html");
		    requestDispatcher.include(req, resp);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.print("<h1> Server busy<h1>");
		try
			{
				con.rollback();
			}
			catch(SQLException p)
			{
				p.printStackTrace();
			}
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      doPost(req,resp);
	}
@Override
public void destroy() {
if(con!=null)
{
	try {
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}
}