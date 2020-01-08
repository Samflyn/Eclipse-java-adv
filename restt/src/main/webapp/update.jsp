<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Update</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function (x) {
			$('#submit').click(function (x) {
				var name = $("#name").val();
				var about = $("#about").val();
				var jdata = { "name": name, "about": about };
				var son = JSON.stringify(jdata);
				$.ajax({
					url: 'update',
					type: 'put',
					data: son,
					dataType: 'json',
					contentType: 'application/json',
					cache: false,
				});
				return false;
			});
		});
	</script>
</head>

<body>
	<form action="update" method="post">
		<h1 style="text-align: center;">Update Details</h1>
		<hr>
		<br> Id: <input type="number" name="id" value='<c:out value="${user.id}"></c:out>' readonly><br>
		<br> Name: <input type="text" name="name" value='<c:out value="${user.name}"></c:out>' readonly><br>
		<br> About: <input type="text" name="about" value='<c:out value="${user.about}"></c:out>'><br>
		<br><br> <input type="submit" value="Submit">
		<input type="reset" value="Clear">
	</form>
</body>

</html>