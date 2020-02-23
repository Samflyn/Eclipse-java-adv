<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Update Prodile</title>
</head>

<body>
    <form action="updateProfile" method="post">
        <h1 style="text-align: center;">Update Details</h1>
        <hr>
        <h3 style="text-align: center; color: red;">${message}</h3>
        <h3 style="text-align: center; color: green;">${success}</h3>
        <br> Id: <input type="number" name="id" value='<c:out value="${customer.id}"></c:out>' readonly><br>
        <br> Email Id: <input type="email" name="email" value='<c:out value="${customer.email}"></c:out>' readonly><br>
        <br> Name: <input type="text" name="name" value='<c:out value="${customer.name}"></c:out>' required>
        <input type="password" name="password" value='<c:out value="${customer.password}"></c:out>' hidden>
        <input type="password" name="rpassword" value='<c:out value="${customer.password}"></c:out>' hidden><br>
        <br> Date Of Birth: <input type="date" name="dob" value='<c:out value="${customer.dob}"></c:out>' required><br>
        <br> Gender: <select name="gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br><br>
        <input type="submit" value="Submit">
        <input type="reset" value="Clear">
    </form>
    <br>
    <h3 style="text-align: center;">
        Go Home: <br> <br> <br> <input type="button" onclick="window.location.href='dashboard';" value="Dashboard">
    </h3>
</body>

</html>