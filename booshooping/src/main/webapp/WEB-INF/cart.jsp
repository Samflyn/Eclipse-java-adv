<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>My Cart</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$(".remove").click(function () {
				var id = this.id;
				var productid = id.replace("remove", "");
				$.ajax({
					url: "removecart",
					type: "post",
					data: {
						productid
					}
				});
				setTimeout(function () {
					location.reload();
				}, 500);
			});
			$(".minus").click(function () {
				var id = this.id;
				var productid = id.replace("minus", "");
				$.ajax({
					url: "minus",
					type: "post",
					data: {
						productid
					}
				});
				setTimeout(function () {
					location.reload();
				}, 500);
			});
			$(".plus").click(function () {
				var id = this.id;
				var productid = id.replace("plus", "");
				$.ajax({
					url: "plus",
					type: "post",
					data: {
						productid
					}
				});
				setTimeout(function () {
					location.reload();
				}, 500);
			});
			$('input:radio').click(function () {
				$("#add").prop("disabled", true);
				if ($(this).hasClass('add')) {
					$("#add").prop("disabled", false);
				}
			});
		});
	</script>
</head>

<body>
	<h1 style="text-align: center;">My Cart</h1>
	<hr>
	<c:set var="total" value="${0}" />
	<table style="width: 100%" id="product">
		<tr>
			<th>Product Id</th>
			<th>Name</th>
			<th></th>
			<th>Quantity</th>
			<th></th>
			<th>Price</th>
			<th></th>
		</tr>
		<c:forEach items="${cart}" var="product" varStatus="roll">
			<tr>
				<td style="text-align: center;">${product.productid}</td>
				<td style="text-align: center;">${product.name}</td>
				<td style="text-align: center;"><input type="button" value="-" id="minus${product.productid}"
						class="minus"></td>
				<td style="text-align: center;">${product.quantity}</td>
				<td style="text-align: center;"><input type="button" value="+" id="plus${product.productid}"
						class="plus"></td>
				<td style="text-align: center;" id="price${roll.count}">${product.price}</td>
				<c:set var="total" value="${total + product.price*product.quantity}" />
				<td style="text-align: center;"><input type="button" value="remove" id="remove${product.productid}"
						class="remove"></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>
	<c:if test="${empty cart}">
		<h3 style="text-align: center;">Your cart is empty. <br><br>
			Go Shopping... <br><br>
			<input type="button" onclick="window.location.href='category';" value="Shopping"><br><br><br>
			Or go Home : <input type="button" onclick="window.location.href='dashboard';" value="Dashboard">
		</h3>
	</c:if>
	<c:if test="${not empty cart}">
		<h3 style="text-align: center;">
			Total :
			<div id="total">
				<c:out value="${total}"></c:out>
			</div>
			<br> <br>Select a Shipping Address : <br> <br>
			<form action="pay" method="post">
				<c:forEach items="${address}" var="addr" varStatus="roll">
					<input type="radio" name="address" value="${addr.address}" id="address" checked> ${addr.address}<br>
				</c:forEach>
				<br>
				<input type="radio" name="address" value="" class="add">Or add a new address :<br>
				<br><input type="text" name="total" id="total" hidden value="<c:out value=" ${total}"></c:out>">
				<textarea name="add" id="add" cols="60" rows="4" disabled="disabled"></textarea>
				<br> <br> <input type="submit" value="pay" id="pay">
				<input type="button" onclick="window.location.href='dashboard';" value="Cancel">
			</form>
		</h3>
	</c:if>
</body>

</html>