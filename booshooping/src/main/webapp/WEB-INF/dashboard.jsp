<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Dashboard</title>
</head>

<body>
	<h1 style="text-align: center;">
		Welcome,
		<c:out value="${customer.name}"></c:out>
	</h1>
	<hr>
	<h1 style="text-align: end;">
		<input type="button" onclick="window.location.href='logout';" value="logout">
	</h1>
	<br>
	<br>
	<br>
	<h3 style="text-align: center;">
		Shop by Category : <input type="button" onclick="window.location.href='category';" value="Category"><br>
		<br> My Cart : <input type="button" onclick="window.location.href='mycart';" value="Cart"><br>
		<br> My Address : <input type="button" onclick="window.location.href='address';" value="My Address"><br>
		<br> My Orders : <input type="button" onclick="window.location.href='orders';" value="Orders"><br>
	</h3>
</body>

</html>