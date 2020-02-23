<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Attention</title>
</head>

<body>
	<h1 style="text-align: center;">Attention</h1>
	<hr>
	<h3 style="text-align: center;color: red;">The following products are no longer available please remove them from
		your cart before checkout...</h3>
	<table style="width: 100%">
		<tr>
			<th>Product Id</th>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
			<th></th>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<td style="text-align: center;">${product.productid}</td>
				<td style="text-align: center;">${product.name}</td>
				<td style="text-align: center;">${product.quantity}</td>
				<td style="text-align: center;">${product.price}</td>
			</tr>
		</c:forEach>
	</table>
	<br><br><br><br>
	<div style="text-align: center;">
		Back to cart : <input type="button" onclick="window.location.href='mycart';" value="Cart"><br><br>
		Or go Home : <input type="button" onclick="window.location.href='dashboard';" value="Dashboard">
	</div>
</body>

</html>