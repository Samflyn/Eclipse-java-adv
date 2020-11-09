<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Registration Page</title>
</head>

<body>
    <form action="register" method="post">
        Name: <input type="text" name="name" placeholder="Name"><br><br>
        Password: <input type="password" name="password" placeholder="Password"><br><br>
        Re-Password: <input type="password" name="rpassword" placeholder="Re-Password"><br><br>
        Role: <input type="text" name="role" placeholder="Role"><br><br>
        Email: <input type="email" name="email" placeholder="Email"><br><br>
        <input type="submit" value="submit">
        <input type="reset" value="clear">
    </form>
</body>

</html>