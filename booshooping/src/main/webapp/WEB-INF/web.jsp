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
            $("#add1,#add2,#add3,#add4,#add5").click(function () {
                var quantity;
                var id = this.id;
                switch (id) {
                    case "add1":
                        quantity = "quantity1";
                        break;

                    case "add2":
                        quantity = "quantity2";
                        break;

                    case "add3":
                        quantity = "quantity3";
                        break;

                    case "add4":
                        quantity = "quantity4";
                        break;

                    case "add5":
                        quantity = "quantity5";
                        break;
                }
                var value = parseInt($('#' + quantity).val(), 10) + 1;
                $("#" + quantity).val(value);
                return false;
            });
            $("#minus1,#minus2,#minus3,#minus4,#minus5").click(function () {
                var quantity;
                var id = this.id;
                switch (id) {
                    case "minus1":
                        quantity = "quantity1";
                        break;

                    case "minus2":
                        quantity = "quantity2";
                        break;

                    case "minus3":
                        quantity = "quantity3";
                        break;

                    case "minus4":
                        quantity = "quantity4";
                        break;

                    case "minus5":
                        quantity = "quantity5";
                        break;
                }
                var value = parseInt($("#" + quantity).val(), 10) - 1;
                if (value < 0) {
                    $("#" + quantity).val(0);
                }
                else {
                    $("#" + quantity).val(value);
                } return false;
            })
            $('#tocart1,#tocart2,#tocart3,#tocart4,#tocart5').click(function () {
                var i;
                var id = this.id;
                switch (id) {
                    case "tocart1":
                        i = 1;
                        break;

                    case "tocart2":
                        i = 2;
                        break;

                    case "tocart3":
                        i = 3;
                        break;

                    case "tocart4":
                        i = 4;
                        break;

                    case "tocart5":
                        i = 5;
                        break;
                }
                var productid = $("#id" + i).text();
                var name = $("#name" + i).text();
                var quantity = $("#quantity" + i).val();
                var price = $("#price" + i).text();
                if (quantity == 0) {
                    alert("Please select some quantity!")
                } else {
                    $.ajax({
                        url: "tocart",
                        type: "post",
                        data: {
                        	productid, name, quantity, price
                        }
                    });
                }
            });
        });
    </script>
</head>

<body>
    <h1 style="text-align: center;">Product List</h1>
    <h1 style="text-align: end;">
        <input type="button" onclick="window.location.href='mycart';" value="My Cart"></h1>
    <table style="width:100%" id="product">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Quantity</th>
            <th>About</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${products}" var="product" varStatus="roll">
            <tr>
                <td style="text-align: center;" id="id${roll.count}">${product.id}</td>
                <td style="text-align: center;" id="name${roll.count}">${product.name}</td>
                <td style="text-align: center;">
                    <button id="minus${roll.count}">-</button>
                    <input type="text" id="quantity${roll.count}" value="0" size="1">
                    <button id="add${roll.count}">+</button>
                </td>
                <td style="text-align: center;" id="about">${product.about}</td>
                <td style="text-align: center;" id="price${roll.count}">${product.price}</td>
                <td style="text-align: center;"><input type="button" value="Add to cart" id="tocart${roll.count}"></td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
    <h1 style="text-align: center;">
        <input type="button" onclick="window.location.href='orders';" value="My Orders">
        <input type="button" onclick="window.location.href='address';" value="Addresses">
    </h1>
    <br><br>
    <h1 style="text-align: center;">
        <input type="button" onclick="window.location.href='logout';" value="Logout">
    </h1>
</body>

</html>