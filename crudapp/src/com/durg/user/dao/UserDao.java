package com.durg.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.durga.db.connect.ConnectionProvider;
import com.durga.user.model.UserBean;

public class UserDao {

    /*private Connection conn;
    

    public UserDao() {
    	conn = ConnectionProvider.getConnection();        
       
    }*/
	public Connection getConn() 
	{
		Connection conn = null;
		try
		{
			String driver = "oracle.jdbc.driver.OracleDriver";
            String url = "jdbc:oracle:thin:@192.168.1.189:1523:sailodba";
            String user = "apps";
            String password = "apps";
		Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		return conn;
	}

    public void addUser(UserBean userBean) {
        try {
        	Connection conn = getConn();
        	 PreparedStatement pst = conn.prepareStatement("INSERT INTO users values(?,?,?,?,?,?,?)");
        	pst.setString(1, userBean.getUserId());
        	pst.setString(2, userBean.getUserPassword());
        	pst.setString(3, userBean.getUserFirstName());
        	pst.setString(4, userBean.getUserMiddleName());
        	pst.setString(5, userBean.getUserLastName());        	
        	pst.setString(6, userBean.getUserGender());
        	pst.setString(7, userBean.getUserZipCode());
            pst.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void removeUser(String userId) {
        try {
        	Connection conn = getConn();
        	String sql = "DELETE FROM users WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
      }

    public void editUser(UserBean userBean) {    	
    	try {
    		Connection conn = getConn();
    		String sql = "UPDATE users SET user_first_name=?, user_middle_name=?,user_last_name=?,user_gender=?,user_zip_code=? WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
                  	
        	pst.setString(1, userBean.getUserFirstName());
        	pst.setString(2, userBean.getUserMiddleName());
        	pst.setString(3, userBean.getUserLastName());        	
        	pst.setString(4, userBean.getUserGender());
        	pst.setString(5, userBean.getUserZipCode());
        	pst.setString(6, userBean.getUserId());
            pst.executeUpdate();            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List <UserBean> getAllUsers() {
        List <UserBean> users = new ArrayList<UserBean>();
        try {
        	Connection conn = getConn();
        	String sql = "SELECT * FROM users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	UserBean userBean = new UserBean();
            	userBean.setUserId(rs.getString(1));           	
            	userBean.setUserFirstName(rs.getString(3));
            	userBean.setUserMiddleName(rs.getString(4));
            	userBean.setUserLastName(rs.getString(5));
            	userBean.setUserGender(rs.getString(6));
            	userBean.setUserZipCode(rs.getString(7));
            	users.add(userBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public UserBean getUserById(String userId) {
    	UserBean userBean = null;
        try {
        	Connection conn = getConn();
        	String sql = "SELECT * FROM users WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	userBean = new UserBean();
            	userBean.setUserId(rs.getString(1));           	
            	userBean.setUserFirstName(rs.getString(3));
            	userBean.setUserMiddleName(rs.getString(4));
            	userBean.setUserLastName(rs.getString(5));
            	userBean.setUserGender(rs.getString(6));
            	userBean.setUserZipCode(rs.getString(7));                          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userBean;
    }
}
