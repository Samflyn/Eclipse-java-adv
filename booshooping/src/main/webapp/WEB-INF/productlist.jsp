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
            $(".add").click(function () {
                var id = this.id;
                var quantity = "quantity" + id.replace("add", "");
                var value = parseInt($('#' + quantity).val(), 10) + 1;
                $("#" + quantity).val(value);
                return false;
            });
            $(".minus").click(function () {
                var id = this.id;
                var quantity = "quantity" + id.replace("minus", "");
                var value = parseInt($("#" + quantity).val(), 10) - 1;
                if (value < 0) {
                    $("#" + quantity).val(0);
                }
                else {
                    $("#" + quantity).val(value);
                } return false;
            })
            $('.tocart').click(function () {
                var id = this.id;
                var i = id.replace("tocart", "");
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
                    <button id="minus${roll.count}" class="minus">-</button>
                    <input type="text" id="quantity${roll.count}" value="0" size="1">
                    <button id="add${roll.count}" class="add">+</button>
                </td>
                <td style="text-align: center;" id="about">${product.about}</td>
                <td style="text-align: center;" id="price${roll.count}">${product.price}</td>
                <td style="text-align: center;"><input type="button" value="Add to cart" id="tocart${roll.count}"
                        class="tocart"></td>
            </tr>
        </c:forEach>
    </table>
    <br><br>
    <h1 style="text-align: center;">
        <input type="button" onclick="window.location.href='address';" value="My Address">
    </h1>
    <br><br>
</body>

</html>