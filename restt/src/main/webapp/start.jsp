<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Register</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function (x) {
			$('#submit').click(function (x) {
				var name = $("#name").val();
				var about = $("#about").val();
				var jdata = { "name": name, "about": about };
				var son = JSON.stringify(jdata);
				$.ajax({
					url: 'register',
					type: 'post',
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
	<form action="register" method="post" name="myform">
		<div>
			<div id="message"></div>
			<h1 style="text-align: center;">Registration</h1>
			<hr>
			<br> Name: <input type="text" name="name" id="name"><br>
			<br> About: <input type="text" name="about" id="about"><br>
			<br> <input type="submit" id="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
	</form>
</body>

</html>