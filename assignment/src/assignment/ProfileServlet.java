package assignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement pst=null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

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
	
		pst=con.prepareStatement("select  * from vvemp where USERNAME=? ");
	    HttpSession session=req.getSession(false);
		String userName=(String)session.getAttribute("uid");  
		pst.setString(1,userName );
		
		ResultSet result = pst.executeQuery();
		if(result.next())
		{
			String Username = result.getString(1);
			String Firstname = result.getString(1);
			String Lastname = result.getString(1);
			Date DOB = result.getDate(1);
			String gender = result.getString(1);
			
			out.println(
					               "<h1>UserID ="+Username
					               +"<br> FirstName ="+Firstname
					               +"<br> LastName ="+Lastname
					               +"<br> DateOf Birth ="+DOB
					               +"<br> Gender ="+gender+"</h1>");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	}
		

