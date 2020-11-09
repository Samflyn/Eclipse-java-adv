<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Register Form</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="register.js">
	</script>
</head>

<body>
	<form action="register" method="post">
		<div>
			<div id="message"></div>
			<h1 style="text-align: center;">Registration Form</h1>
			<hr>
			<br> Name: <input type="text" name="name" id="name"><br>
			<br> Password: <input type="password" name="password" id="password"><br>
			<br> Re-Password: <input type="password" name="rpassword" id="rpassword"><br>
			<br> Date Of Birth: <input type="date" name="date" id="date"><br>
			<br> Gender: <select name="gender" id="gender">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
			</select><br>
			<br> Department: <select name="dept" id="dept">
				<option value="HR">HR</option>
				<option value="Dev">Dev</option>
				<option value="Database">Database</option>
				<option value="Sales">Sales</option>
			</select><br><br>
			Role: <select name="role" id="role">
				<option value="Admin">Admin</option>
				<option value="Employee">Employee</option>
				<option value="Dept Head">Dept Head</option>
			</select><br>
			<br> <input type="submit" id="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
	</form>
</body>

</html>