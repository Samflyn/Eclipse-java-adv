<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Profile</title>
</head>

<body>
    <h1 style="text-align: center;">Profile Details</h1>
    <hr>
    <h3 style="text-align: center; color: green;">${success}</h3>
    <br> Id: ${customer.id}<br>
    <br> Email-Id: ${customer.email}<br>
    <br> Name: ${customer.name}<br>
    <br> Date Of Birth: ${customer.dob}<br>
    <br> Gender: ${customer.gender}<br><br>
    <input type="button" onclick="window.location.href='editProfile';" value="edit">
    <br>
	<h3 style="text-align: center;">
		Go Home: <br> <br> <br> <input type="button" onclick="window.location.href='dashboard';" value="Dashboard">
	</h3>
</body>

</html>