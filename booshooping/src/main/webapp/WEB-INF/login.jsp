<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Login Form</title>
</head>

<body>
	<form action="login" method="post">
		<br>
		<h1 style="text-align: center;">Welcome to Shopify</h1>
		<hr>
		<br>
		<h3 style="text-align: center; color: red;">${message}</h3>
		<h3 style="text-align: center; color: green;">${logout}</h3>
		<br> <br>
		<h3 style="text-align: center;">
			User Name:<input type="text" name="name" required><br> <br>
			Password:<input type="password" name="password" required>
		</h3>
		<h2 style="text-align: center;">
			<input type="submit" value="Login">
		</h2>
	</form>
</body>

</html>