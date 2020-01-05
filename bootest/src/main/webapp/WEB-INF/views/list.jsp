<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>All Users</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">$(document).ready(function () {
	    $("button").click(function () {
	        var id = $(this).attr("value");
	        var page = $(this).attr("id");
	        if (page == "delete") {
	            var r = confirm("Are you sure to delete : " + id);
	            if (r == true) {
	                $.ajax({
	                    url: page,
	                    type: "post",
	                    data: { id },
	                    cache: false,
	                    success: function(){
	                        location.replace("list");
	                      },
	                      error: function(){
	                    	  location.replace("list");
	                      }
	                });
	            }
	        } else {
	            location.replace("update?id=" + id);
	        }
	    });
	});</script>
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
				<td><c:out value="${employee.id}"/></td>
				<td><c:out value="${employee.name}"/></td>
				<td><c:out value="${employee.password}"/></td>
				<td><c:out value="${employee.dob}"/></td>
				<td><c:out value="${employee.gender}"/></td>
				<td><c:out value="${employee.role}"/></td>
				<td> <button id="delete" value="${employee.id}">delete</button></td>
				<td> <button id="update" value="${employee.id}">update</button></td>
			</tr>
			</c:forEach>
		</table>
	</div>
</body>

</html>