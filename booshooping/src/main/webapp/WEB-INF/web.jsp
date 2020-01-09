<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Web Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#add").click(function () {
                var value = parseInt($("#quantity").val(), 10) + 1;
                $("#quantity").val(value);
                return false;
            });
            $("#minus").click(function () {
                var value = parseInt($("#quantity").val(), 10) - 1;
                if (value < 0) {
                    $("#quantity").val(0);
                }
                else {
                    $("#quantity").val(value);
                } return false;
            });
            $('#tocart').click(function () {
                var id = $("#id").text();
                var name = $("#name").text();
                var quantity = $("#quantity").val();
                var price = $("#price").text();
                $.ajax({
                    url: "tocart",
                    type: "post",
                    data: {
                        id, name, quantity, price
                    }
                });
            });
        });
    </script>
</head>

<body>
    <h1 style="text-align: center;">Product List</h1>
    <table style="width:100%" id="product">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td style="text-align: center;" id="id">${product.id}</td>
                <td style="text-align: center;" id="name">${product.name}</td>
                <td style="text-align: center;"><button id="minus">-</button><input type="text" name="quantity"
                        id="quantity" value="0" size="1"><button id="add">+</button></td>
                <td style="text-align: center;" id="price">${product.price}</td>
                <td style="text-align: center;"><input type="button" value="Add to cart" id="tocart"></td>
            </tr>
        </c:forEach>
    </table>
</body>

</html>