<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>HomePage</title>
</head>

<body>
    <br>
    <h1 style="text-align: center;">Welcome , Mr. <c:out value="${employee.name}"></c:out>
    </h1>
    <hr>
    <c:if test="${employee.role == 'Admin'}">
        <h1 style="text-align: end;">
            <input type="button" onclick="window.location.href='logout';" value="logout">
        </h1>
        <h3 style="text-align: center;">Your last login time is: <c:out value="${employee.loginDate}"></c:out></h3>
        <h3 style="text-align: center;">Since you are an Admin, you can create Admin and employees.</h3>
        <h3 style="text-align: center;">
        	Profile Details :
        	ID : <c:out value="${employee.id}"></c:out><br><br>
        	Date of Birth : <c:out value="${employee.dob}"></c:out><br><br>
        	Gender : <c:out value="${employee.gender}"></c:out><br><br>
        	Role : <c:out value="${employee.role}"></c:out><br><br>
            User List : <input type="button" onclick="window.location.href='list';" value="List"><br><br>
            Update Profile : <input type="button" onclick="window.location.href='update';" value="update">
        </h3>
    </c:if>
    <c:if test="${employee.role == 'Employee'}">
        <h1 style="text-align: end;">
            <input type="button" onclick="window.location.href='logout';" value="logout">
        </h1>
        <h3 style="text-align: center;">Your last login time is: <c:out value="${employee.loginDate}"></c:out></h3>
        <h3 style="text-align: center;">Since you are Employee you can check only your profile.</h3>
        <br>
        <h3 style="text-align: center;">
        	Profile Details :
        	ID : <c:out value="${employee.id}"></c:out>
        	Date of Birth : <c:out value="${employee.dob}"></c:out>
        	Gender : <c:out value="${employee.gender}"></c:out>
        	Role : <c:out value="${employee.role}"></c:out>
            Update Profile : <input type="button" onclick="window.location.href='update';" value="update">
        </h3>
    </c:if>
</body>

</html>