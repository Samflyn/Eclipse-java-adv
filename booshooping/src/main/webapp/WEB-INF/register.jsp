<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Register Form</title>
</head>

<body>
	<form action="register" method="post">
		<div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr>
			<h3 style="text-align: center; color: red;">${fail}</h3>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Name: <input type="text" name="name" required><br>
			<br> Password: <input type="password" name="password" required><br>
			<br> Re-Password: <input type="password" name="rpassword" required><br>
			<br><br> <input type="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
	</form>
</body>

</html>