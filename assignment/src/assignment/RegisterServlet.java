package assignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{
	
	Connection con=null;
	PreparedStatement pst=null;
	PreparedStatement pst1=null;

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
			pst=con.prepareStatement("insert into vvemp values (?,?,?,?,?,?)");
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
			String RePassword = req.getParameter("rpwd");
			String Fname = req.getParameter("firstname");
			String Lname = req.getParameter("lastname");
			String DOB= req.getParameter("DOB");
			String Gen = req.getParameter("gender");
			
		  if(!Password.equals(RePassword))
			{
				out.println("<h3>pasword mis mateched</h3>");
				  RequestDispatcher requestDispatcher = req.getRequestDispatcher("RegisterForm.html");
				    requestDispatcher.include(req, resp);
				    return;
			}
			pst.setString(1,Uname );
			pst.setString(2,Password );
			pst.setString(3,Fname );
			pst.setString(4,Lname );
			pst.setString(5,Gen );
			pst.setString(6, DOB);
			pst1=con.prepareStatement("select count(*) from vvemp where USERNAME=?");
			pst1.setString(1, Uname);
			ResultSet executeQuery = pst1.executeQuery();
			 executeQuery.next();
			 int count = executeQuery.getInt(1);
			if(count==1)
			{
				out.println("<h3>Userid already exists</h3>");
			    RequestDispatcher requestDispatcher = req.getRequestDispatcher("RegisterForm.html");
			    requestDispatcher.include(req, resp);
			}
			else
			{
			pst.executeUpdate();
			out.println("<h2>Succefully updated<h2>");
			con.commit();
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("RegisterForm.html");
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
