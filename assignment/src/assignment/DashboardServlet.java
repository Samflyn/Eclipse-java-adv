package assignment;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
	    PrintWriter out=response.getWriter();  

	    RequestDispatcher linkRequestDispatcher=request.getRequestDispatcher("WebLink.html");
	    linkRequestDispatcher.include(request, response);        
	    HttpSession session=request.getSession(false);
	if(session!=null)
	    {  
	String userName=(String)session.getAttribute("uid");  

	out.print("Hello, "+userName+" Welcome to Dashboard");  
	    }
	else
	    {  
	out.print("Please login first");  
	        RequestDispatcher loginRequestDispatcher=request.getRequestDispatcher("Login.html");            
	loginRequestDispatcher.include(request, response);
		
	    }  
	out.close();
	}  
	}
