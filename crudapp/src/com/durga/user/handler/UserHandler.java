package com.durga.user.handler;


import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.durg.user.dao.UserDao;
import com.durga.user.model.UserBean;

public class UserHandler extends HttpServlet {    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String INSERT = "/jsp/user.jsp";
    private static String Edit = "/jsp/edit.jsp";
    private static String UserRecord = "/jsp/listUser.jsp";
    
    private UserDao dao;
/*
    public UserHandler() {
        super();
        dao = new UserDao();
    }*/
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirect="";
        String userId = request.getParameter("userid");        
        String action = request.getParameter("action");
        if(!((userId)== null) && action.equalsIgnoreCase("insert"))
        {
        	
        	UserBean userBean = new UserBean();
        	
        	userBean.setUserId(request.getParameter("userid"));
        	userBean.setUserFirstName(request.getParameter("firstName"));
        	userBean.setUserMiddleName(request.getParameter("middleName"));
        	userBean.setUserLastName(request.getParameter("lastName"));
        	userBean.setUserPassword(request.getParameter("userpwd"));        	
        	userBean.setUserGender(request.getParameter("gender"));
        	userBean.setUserZipCode(request.getParameter("zip"));      	
        	
        	dao.addUser(userBean);
        	
        	redirect = UserRecord;
            request.setAttribute("users", dao.getAllUsers());    
           	System.out.println("Record Added Successfully");
        }
        else if (action.equalsIgnoreCase("delete")){
            userId = request.getParameter("userId");
           
            dao.removeUser(userId);
            redirect = UserRecord;
            request.setAttribute("users", dao.getAllUsers());
            System.out.println("Record Deleted Successfully");
        }else if (action.equalsIgnoreCase("editform")){        	
        	redirect = Edit;            
        } else if (action.equalsIgnoreCase("edit")){        	                      
            
            UserBean userBean = new UserBean();        	
        	userBean.setUserId(request.getParameter("userId"));
        	userBean.setUserFirstName(request.getParameter("firstName"));
        	userBean.setUserMiddleName(request.getParameter("middleName"));
        	userBean.setUserLastName(request.getParameter("lastName"));
        	userBean.setUserPassword(request.getParameter("userpwd"));        	
        	userBean.setUserGender(request.getParameter("gender"));
        	userBean.setUserZipCode(request.getParameter("zip")); 
            dao.editUser(userBean);
            
            request.setAttribute("user", userBean);
            redirect = UserRecord;
           
            System.out.println("Record updated Successfully ;");
         } else if (action.equalsIgnoreCase("listUser")){
            redirect = UserRecord;
            request.setAttribute("users", dao.getAllUsers());
         } else {
            redirect = INSERT;
        }

        RequestDispatcher rd = request.getRequestDispatcher(redirect);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
