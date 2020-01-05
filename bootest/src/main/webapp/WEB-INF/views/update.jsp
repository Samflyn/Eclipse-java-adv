<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Update Page</title>
</head>

<body>
<form action="update" method="post">
		<h1 style="text-align: center;">Update Details</h1>
		<hr>
			<h3 style="text-align: center; color: green;">${success}</h3>
			<br> Id: <input type="number" name="id" value='<c:out value="${employee.id}"></c:out>' readonly><br>
			<br> Name: <input type="text" name="name" value='<c:out value="${employee.name}"></c:out>' readonly><br>
			<br> Password: <input type="password" name="password" value='<c:out value="${employee.password}"></c:out>' ><br>
			<br> Re-Password: <input type="password" name="rpassword" value='<c:out value="${employee.password}"></c:out>' ><br>
			<br> Date Of Birth: <input type="date" name="dob" value='<c:out value="${employee.dob}"></c:out>' ><br>
			<br> Gender: <select name="gender">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
			</select><br><br>
			
			Role: <select name="role">
			<c:if test="${employee.role == 'Admin'}">
				<option value="Admin">Admin</option>
				</c:if>
				<option value="Employee">Employee</option>
			</select>			
			<br><br> <input type="submit" value="Submit">
			<input type="reset" value="Clear">
			</form>
</body>

</html>