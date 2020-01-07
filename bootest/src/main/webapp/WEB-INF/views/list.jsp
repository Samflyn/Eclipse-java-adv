<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>All Users</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript" src="list.js"></script>
</head>

<body>
	<div id="content">
		<h2 style="text-align: center;">All User List</h2>
		<table style="width: 100%">
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Password</th>
				<th>Date Of Birth</th>
				<th>Gender</th>
				<th>Role</th>
			</tr>
			<c:forEach items="${list}" var="employee">
				<tr>
					<td>
						<c:out value="${employee.id}" />
					</td>
					<td>
						<c:out value="${employee.name}" />
					</td>
					<td>
						<c:out value="${employee.password}" />
					</td>
					<td>
						<c:out value="${employee.dob}" />
					</td>
					<td>
						<c:out value="${employee.gender}" />
					</td>
					<td>
						<c:out value="${employee.role}" />
					</td>
					<td> <button id="delete" value="${employee.id}">delete</button></td>
					<td> <button id="update" value="${employee.id}">update</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>

</html>