<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Category</title>
</head>

<body>
    <h1 style="text-align: center;">Category Type</h1>
    <hr>
    <br><br><br>
    <h3 style="text-align: center;">
        <c:forEach items="${category}" var="cat">
            <input type="button" onclick="window.location.href='shop?id=${cat}';" value="${cat}"><br><br><br>
        </c:forEach>
    </h3><br>
    <h3 style="text-align: center;">
    Or Go Home: <br><br><br>
    <input type="button" onclick="window.location.href='dashboard';" value="Dashboard">
    </h3>
</body>

</html>