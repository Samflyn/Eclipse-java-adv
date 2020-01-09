<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Delete</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$('#submit').click(function () {
				var id = $("#idd").val();
				var urll = "delete/" + id;
				var r = confirm("Are you sure to delete : " + id);
				if (r == true) {
					$.ajax({
						url: urll,
						type: 'delete',
						data: id,
						dataType: 'json',
						contentType: 'application/json',
						cache: false,
					});
				}
				alert("data sent : "+id);
				return false;
			});
		});
	</script>
</head>

<body>
	<form action="register" method="post" name="myform">
		<div>
			<div id="message"></div>
			<h1 style="text-align: center;">Delete Id</h1>
			<hr>
			<br> Id: <input type="text" name="name" id="idd"><br>
			<br> <input type="submit" id="submit" value="Submit">
			<input type="reset" value="Clear">
		</div>
	</form>
</body>

</html>