<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HomePage</title>
</head>
<body>
<% 
session = (HttpSession) request.getAttribute("session");
String role = (String) request.getAttribute("role");
session.setAttribute("role", role);
if(session != null){
if(role.equals("Admin")){
%>
<br><br>
<h1 style="text-align: center;">Welcome , Mr. ${name}</h1>
    <hr>
    <h1 style="text-align: end;">
        <input type="button" onclick="window.location.href='login';" value="logout">
    </h1>
    <br><br>
    <h3 style="text-align: center;">Since you are an Admin you can create Managers and employees.</h3>
    <br>
    <h3 style="text-align: center;">
        Register : <input type="button" onclick="window.location.href='rootregister.jsp';" value="Register">
    </h3>
<%}else if(role.equals("Manager")) {%>
<br><br>
    <h1 style="text-align: center;">Welcome , Mr. ${name}</h1>
    <hr>
    <h1 style="text-align: end;">
        <input type="button" onclick="window.location.href='login';" value="logout">
    </h1>
    <br><br>
    <h3 style="text-align: center;">Since you are a Manager, you can assign task to an employee.</h3>
    <h3 style="text-align: center;">You can also track the status of the tasks.</h3>
    <br>
    <h3 style="text-align: center;">
        Create Task : <input type="button" onclick="window.location.href='rootregister.jsp';" value="Task"><br><br>
        Check Status : <input type="button" onclick="window.location.href='rootregister.jsp';" value="Status">
    </h3>
<%}else{ %>
<br><br>
    <h1 style="text-align: center;">Welcome , Mr. ${name}</h1>
    <hr>
    <h1 style="text-align: end;">
        <input type="button" onclick="window.location.href='login';" value="logout">
    </h1>
    <br><br>
    <h3 style="text-align: center;">You are an employee.</h3>
    <h3 style="text-align: center;">You can check the tasks assigned to you and update the status of it.</h3>
    <br>
    <h3 style="text-align: center;">
        Check Task : <input type="button" onclick="window.location.href='rootregister.jsp';" value="Task"><br><br>
        Update Status : <input type="button" onclick="window.location.href='rootregister.jsp';" value="Status">
    </h3>
<%} }
else{%>
<br><br><br>
    <h1 style="text-align: center; color: red;">Please login first</h1>
    <hr>
    <br><br>
    <h3 style="text-align: center;">
        Login : <input type="button" onclick="window.location.href='login.jsp';" value="Login">
    </h3>
<%} %>
</body>
</html>