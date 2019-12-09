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
	<form action="register" method="post">
		<div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr><br><br>
			<h3 style="text-align: center; color: red;">${fail}</h3>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Name: <input type="text" name="name"><br>
			<br> Password: <input type="password" name="password"><br>
			<br> Re-Password: <input type="password" name="rpassword"><br>
			<br> Date Of Birth: <input type="date" name="date"><br>
			<br> Gender: <select name="gender">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
			</select><br><br><br>
			Role: <select name="role">
				<option value="Employee">Employee</option>
			</select><br><br><br>
			Manager: <select name="manager">
			<%SessionFactory sf = HibCon.getSessionFactory();
			Session s = sf.openSession();
			s.createQuery("from ");
			%>
			<option value = ></option>
			</select>
			<br> <input type="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
	</form>
</body>

</html>