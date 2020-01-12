<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Address</title>
</head>

<body>
    <h1 style="text-align: center;">Addressess</h1>
    <hr>
    <table style="width:100%" id="product">
        <tr>
            <th>Id</th>
            <th>Address</th>
        </tr>
        <c:forEach items="${address}" var="addr" varStatus="roll">
            <tr>
                <td style="text-align: center;" id="id${roll.count}">${roll.count}</td>
                <td style="text-align: center;" id="address${roll.count}">${addr.address}</td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
    <form action="addaddress" method="post">
        <div style="text-align: center;">
            <h3>Or add a new address</h3>
            <textarea name="add" id="address" cols="100" rows="10"></textarea><br><br>
            <input type="submit" value="add">
            <input type="reset" value="clear">
        </div>
    </form>
</body>

</html>