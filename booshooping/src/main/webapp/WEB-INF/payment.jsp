<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Payment</title>
</head>

<body>
    <h3 style="text-align: center;">Success !<br>
        Your payment has been successfull <br><br>
        Estimated Delivery date - <c:out value="${day}"></c:out> <br><br>
        Payment Details : <br><br>
        Transaction Id : <c:out value="${tx.txid}"></c:out><br><br>
        Date of purchase : <c:out value="${tx.date}"></c:out><br><br>
        Shipping Address : <c:out value="${tx.address}"></c:out><br><br>
        Items : <c:forEach items="${tx.items}" var="i"><br>
        Name : <c:out value="${i.name}"></c:out><br>
        Quantity : <c:out value="${i.quantity}"></c:out><br>
        Price : <c:out value="${i.price}"></c:out><br>
        </c:forEach><br><hr>
        Total : <c:out value="${tx.total}"></c:out><br><br>
        <input type="button" onclick="window.location.href='dashboard';" value="Go home"></h3>
</body>

</html>