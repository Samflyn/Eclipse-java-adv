<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New User</title>
</head>
<body>
<form method="POST" action='userHandler' name="frmAddUser">
<input type="hidden" name="action" value="insert" />
<p><b>Add New Record</b></p>
<table>

<tr>
<td>First Name</td>
<td><input type="text" name="firstName"  /></td>
</tr>
<tr>
<td>Middle Name</td>
<td><input type="text" name="middleName" /></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type="text" name="lastName" /></td>
</tr>
<tr>
<td>Preferred User ID</td>
<td><input type="text" name="userid" /></td>
</tr>

<tr>
<td>Preferred Password</td>
<td><input type="password" name="userpwd" /></td>
</tr>

<tr>
<td>Retype Password</td>
<td><input type="text" name="userrtpwd" /></td>
</tr>

<tr>
<td>Gender</td>
<td>    
<input type="radio" name="gender" value="male" checked> Male<br>
<input type="radio" name="gender" value="female"> Female<br>
<input type="radio" name="gender" value="other"> Other
</td>
</tr>
<tr>
<td>Zip Code</td>
<td><input type="text" name="zip" /></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="Submit" /></td>
</tr>
</table>
</form>

<p><a href="userHandler?action=listUser">View-All-Records</a></p>
</body>
</html>