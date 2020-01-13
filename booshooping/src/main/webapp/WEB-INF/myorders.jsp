<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Orders</title>
</head>

<body>
    <h1 style="text-align: center;">My Orders</h1>
    <hr>
    <table style="width:100%" id="product">
        <tr>
            <th>Order No</th>
            <th>Transaction Id</th>
            <th>Date of Purchase</th>
            <th>Address</th>
            <th>Total</th>
            <th>Status</th>
        </tr>
        <c:forEach items="${tx}" var="ts" varStatus="roll">
            <tr>
                <td style="text-align: center;">${roll.count}</td>
                <td style="text-align: center;">${ts.txid}</td>
                <td style="text-align: center;">${ts.date}</td>
                <td style="text-align: center;">${ts.address}</td>
                <td style="text-align: center;">${ts.total}</td>
                <td style="text-align: center;">${ts.status}</td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
    <h3 style="text-align: center;">
        Go Home : <input type="button" onclick="window.location.href='web';" value="home">
    </h3>
</body>

</html>