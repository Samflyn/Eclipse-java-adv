<%@page import="com.test.service.ListService"%>
<%@page import="com.test.bean.SamEmployees"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.hibernate.query.Query"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.test.conn.HibCon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Register Form</title>
</head>

<body>
<%
session = request.getSession(false);
String role = (String) session.getAttribute("role");
%>
	<form action="register" method="post">
	<%if(role !=null){ %>
	<%if(role.equals("Admin")) {%>
	<div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr>
			<h3 style="text-align: center; color: red;">${fail}</h3>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Name: <input type="text" name="name"><br>
			<br> Password: <input type="password" name="password"><br>
			<br> Re-Password: <input type="password" name="rpassword"><br>
			<br> Date Of Birth: <input type="date" name="date"><br>
			<br> Gender: <select name="gender">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
			</select><br><br>
			Role: <select name="role">
				<option value="Admin">Admin</option>
				<option value="Employee">Employee</option>
				<option value="Manager">Manager</option>
			</select><br><br>
			Manager: <select name="manager">
				<%List list = (List) request.getAttribute("list");
				Iterator itr = list.iterator();
			while(itr.hasNext()){
				SamEmployees se = (SamEmployees) itr.next();
				if(se.getRole().equals("Manager") || se.getRole().equals("Admin")){
			%>
				<option value=<%=se.getId() %>><%=se.getName() %></option>
				<%} }%>
			</select>
			<br><br> <input type="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
		
		<%}} else { %>
		<div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr>
			<input type="button" onclick="window.location.href='login.jsp';" value="Login" style="text-align: end;">
			<h3 style="text-align: center; color: red;">${fail}</h3>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Name: <input type="text" name="name"><br>
			<br> Password: <input type="password" name="password"><br>
			<br> Re-Password: <input type="password" name="rpassword"><br>
			<br> Date Of Birth: <input type="date" name="date"><br>
			<br> Gender: <select name="gender">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
			</select><br><br>
			Role: <select name="role">
				<option value="Employee">Employee</option>
			</select><br><br>
			Manager: <select name="manager">
				<%
				List list = (List) request.getAttribute("list");
				Iterator itr = list.iterator();
			while(itr.hasNext()){
				SamEmployees se = (SamEmployees) itr.next();
				if(se.getRole().equals("Manager")){
			%>
				<option value=<%=se.getId() %>><%=se.getName() %></option>
				<%} }%>
			</select>
			<br><br>
			<input type="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
		<%} %>
	</form>
</body>

</html>