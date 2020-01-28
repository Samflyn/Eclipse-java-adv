<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Items</title>
</head>

<body>
	<table style="width: 75%" id="product">
		<tr>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
		</tr>
		<c:forEach items="${items}" var="i">
			<tr>
				<td style="text-align: center;">${i.name}</td>
				<td style="text-align: center;">${i.quantity}</td>
				<td style="text-align: center;">${i.price}</td>
			</tr>
		</c:forEach>
	</table>
</body>

</html>