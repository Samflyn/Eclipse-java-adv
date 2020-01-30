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
	<h3 style="text-align: center;color: red;">The quantity for following products are no longer available...</h3>
	<table style="width: 100%">
		<tr>
			<th>Product Id</th>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Available Stock</th>
		</tr>
		<c:forEach items="${carts}" var="cart">
			<tr>
				<td style="text-align: center;">${cart.productid}</td>
				<td style="text-align: center;">${cart.name}</td>
				<td style="text-align: center;">${cart.quantity}</td>
				<td style="text-align: center;">${cart.price}</td>
				<c:forEach items="${products}" var="product">
					<c:if test="${product.id eq cart.productid}">
						<td style="text-align: center;">${product.stock}</td>
					</c:if>
				</c:forEach>
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