<%@page import="com.demo.project.ConnectionGen"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Update Page</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="update.js"></script>
</head>

<body>
	<div id="message"></div>
	<div id="content">
		<%!ResultSet rs;
		Connection con;
		String role;%>
		<%
		if(request.getParameter("name") == null){
		String name = (String) session.getAttribute("name");}
		String name = request.getParameter("name");
		role = (String) session.getAttribute("role");
		ConnectionGen cont = new ConnectionGen();
		try{
		con=cont.getConnection(getServletContext());
		con.setAutoCommit(false);
		PreparedStatement ps = con.prepareStatement("select * from employees where name=?");
		ps.setString(1, name);
		ResultSet rs  = ps.executeQuery();
		rs.next();
		%>
		<h1 style="text-align: center;">Update Details</h1>
		<hr>
		<br> Name: <input type="text" name="name" id="name" disabled value="<%=rs.getString(1) %>"><br>
		<br> Password: <input type="text" name="password" id="password" value="<%=rs.getString(2) %>"><br>
		<br> Date Of Birth: <input type="date" name="date" id="date" value="<%=rs.getString(3) %>"><br>
		<br> Gender: <select name="gender" id="gender">
			<option value="none" selected disabled>
				Select an Option
			</option>
			<option value="Male">Male</option>
			<option value="Female">Female</option>
		</select><br>
		<%if(role.contentEquals("Admin")){ %>
		<br> Department: <select name="dept" id="dept">
			<option value="none" selected disabled>
				Select an Option
			</option>
			<option value="HR">HR</option>
			<option value="Dev">Dev</option>
			<option value="Database">Database</option>
			<option value="Sales">Sales</option>
		</select><br><br>
		Role: <select name="role" id="role">
			<option value="none" selected disabled>
				Select an Option
			</option>
			<option value="Admin">Admin</option>
			<option value="Employee">Employee</option>
			<option value="Dept Head">Dept Head</option>
		</select><br> <%} %>
		<br>
		<%}catch(Exception e){
				e.printStackTrace();
			} %><input type="submit" id="submit" value="Submit">
		<input type="reset" value="Clear">
	</div>
</body>

</html>