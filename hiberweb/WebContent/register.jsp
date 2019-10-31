<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register</title>
</head>
<body>
	<form action="register" method="post">
		<table>
			<tr>
				<td>User id :</td>
				<td><input type="text" name="userid" size="30" /></td>
			</tr>
			<tr>
				<td>User Name :</td>
				<td><input type="text" name="userName" size="30" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><input type="password" name="password" size="30" /></td>
			</tr>

			<tr>
				<td>Confirm Password :</td>
				<td><input type="password" name="password1" size="30" /></td>
			</tr>
			<tr>
				<td>email :</td>
				<td><input type="text" name="email" size="30" /></td>
			</tr>
			<tr>
				<td>Phone :</td>
				<td><input type="text" name="phone" size="30" /></td>
			</tr>
			<tr>
				<td>City :</td>
				<td><input type="text" name="city" size="30" /></td>
			</tr>
		</table>
		<p />
		<input type="submit" value="Register" />
	</form>
</body>
</html>