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
<%out.println("This is your home page, only Admin's will be able to create a new user and view all the lists."); 
if(role.contentEquals("Admin")){
	out.println("'Since you are an Admin you can edit, update and delete all users'");
}
%></div>
</body>
</html>