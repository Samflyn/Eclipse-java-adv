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
	<c:if test="${customer.role == 'Admin'}">
		<h3 style="text-align: center;">
			<br> Register : <input type="button" onclick="window.location.href='register';" value="Register"><br>
			<br> Add Products : <input type="button" onclick="window.location.href='addproduct';"
				value="Add Products"><br>
			<br> Manage Products : <input type="button" onclick="window.location.href='manage';" value="List"><br>
		</h3>
	</c:if>
	<c:if test="${customer.role == 'Customer'}">
		<h3 style="text-align: center;">
			Shop by Category : <input type="button" onclick="window.location.href='category';" value="Category"><br>
			<br> My Cart : <input type="button" onclick="window.location.href='mycart';" value="Cart"><br>
			<br> My Address : <input type="button" onclick="window.location.href='address';" value="My Address"><br>
			<br> My Orders : <input type="button" onclick="window.location.href='orders';" value="Orders"><br>
			<br> My Profile : <input type="button" onclick="window.location.href='profile';" value="Profile"><br>
		</h3>
	</c:if>
</body>

</html>