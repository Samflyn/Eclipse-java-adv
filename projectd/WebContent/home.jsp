<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Home</title>
</head>

<body>
	<div>
		<%String role = (String)session.getAttribute("role");%>
		<%out.println("This is your home page.");%><br>
		<%
		out.print("Only Admin's will be able to create a new user and view all the lists.");%><br>
		<%
		if(role.contentEquals("Admin")){
		out.println("'Since you are an Admin you can edit, update and delete all users'");
		} else {
		out.println("'You are not an Admin.'");
		}%>
</div>
</body>

</html>