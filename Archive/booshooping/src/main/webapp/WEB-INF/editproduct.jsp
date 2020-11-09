<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Product</title>
</head>
<body>
<form action="editproduct" method="post">
        <h1 style="text-align: center;">Update Details</h1>
        <hr>
        <br> Id: <input type="number" name="id" value='<c:out value="${product.id}"></c:out>' readonly><br>
        <br> Name: <input type="text" name="name" value='<c:out value="${product.name}"></c:out>' readonly><br>
        <br> About: <input type="text" name="about" value='<c:out value="${product.about}"></c:out>'><br>
        <br> Category: <input type="text" name="category" value='<c:out value="${product.category}"></c:out>' readonly><br>
        <br> Price: <input type="number" name="price" value='<c:out value="${product.price}"></c:out>'><br>
        <br> Stock: <input type="number" name="stock" value='<c:out value="${product.stock}"></c:out>'><br><br>
        <input type="submit" value="Submit">
        <input type="reset" value="Clear">
    </form>
    <br>
    <h3 style="text-align: center;">
        Go To List : <input type="button" onclick="window.location.href='manage';" value="List">
    </h3>
</body>
</html>