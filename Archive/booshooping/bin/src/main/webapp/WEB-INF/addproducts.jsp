<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Add Products</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
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
	<form action="addproducts" method="post">
		<div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr>
			<h3 style="text-align: center; color: red;">${fail}</h3>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Name: <input type="text" name="name" required><br>
			<br> About: <input type="text" name="about" required><br><br>
			Category :<br>
			<c:forEach items="${category}" var="cat">
				<input type="radio" name="category" value="${cat}" id="category" checked> ${cat}<br>
			</c:forEach>
			<br>
			Or add a new category : <br><br>
			<input type="radio" name="category" value="" class="add">
			<input name="category" type="text" id="add" disabled="disabled"></input><br>
			<br> Price: <input type="number" name="price" required>
			<br> <br> <input type="submit" value="Submit"> <input type="reset" value="Clear">
		</div>
	</form>
	<br><br>
	<input type="button" onclick="window.location.href='javascript:history.back()';" value="Go back">
</body>

</html>