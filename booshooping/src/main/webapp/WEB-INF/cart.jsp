<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Cart</title>
</head>

<body>
    <h1 style="text-align: center;">Product List</h1>
    <hr>
    <table style="width:100%" id="product">
        <tr>
            <th>Product Id</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th></th>
        </tr>
        <c:forEach items="${cart}" var="product" varStatus="roll">
            <tr>
                <td style="text-align: center;" id="id${roll.count}">${product.productid}</td>
                <td style="text-align: center;" id="name${roll.count}">${product.name}</td>
                <td style="text-align: center;" id="quantity${roll.count}">${product.quantity}</td>
                <td style="text-align: center;" id="price${roll.count}">${product.price}</td>
                <td style="text-align: center;"><input type="button" value="remove" id="remove${roll.count}"></td>
            </tr>
        </c:forEach>
    </table>
    <br><br><br>
    <h3 style="text-align: center;">
    Total : <div id="sum"></div>
    <br><br>
    <input type="button" value="Pay">
    <input type="button" value="Cancel">
    </h3>
</body>

</html>