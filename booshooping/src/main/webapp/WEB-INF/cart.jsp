<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Cart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#remove1,#remove2,#remove3,#remove4,#remove5").click(function () {
                var productid;
                var id = this.id;
                switch (id) {
                    case "remove1":
                        i = 1;
                        break;

                    case "remove2":
                        i = 2;
                        break;

                    case "remove3":
                        i = 3;
                        break;

                    case "remove4":
                        i = 4;
                        break;

                    case "remove5":
                        i = 5;
                        break;
                }
                var productid = $("#id" + i).text();
                $.ajax({
                    url: "removecart",
                    type: "post",
                    data: {
                        productid
                    }
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
            });
            $("#minus1,#minus2,#minus3,#minus4,#minus5").click(function () {
                var productid;
                var id = this.id;
                switch (id) {
                    case "minus1":
                        i = 1;
                        break;

                    case "minus2":
                        i = 2;
                        break;

                    case "minus3":
                        i = 3;
                        break;

                    case "minus4":
                        i = 4;
                        break;

                    case "minus5":
                        i = 5;
                        break;
                }
                var productid = $("#id" + i).text();
                $.ajax({
                    url: "minus",
                    type: "post",
                    data: {
                        productid
                    }
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
            });
            $("#pay").click(function () {
                var total = $('#total').text();
                if (total == 0) {
                    alert("Please add few products to cart!");
                    return false;
                }
            });
        });
    </script>
</head>

<body>
    <h1 style="text-align: center;">Product List</h1>
    <hr>
    <c:set var="total" value="${0}" />
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
                <c:set var="total" value="${total + product.price*product.quantity}" />
                <td style="text-align: center;"><input type="button" value="minus" id="minus${product.productid}"></td>
                <td style="text-align: center;"><input type="button" value="remove" id="remove${roll.count}"></td>
            </tr>
        </c:forEach>
    </table>
    <br><br><br>
    <h3 style="text-align: center;">
        Total : <div id="total">
            <c:out value="${total}"></c:out>
        </div>
        <br><br>
        Select Address: <br><br>
        <form action="pay" method="post">
            <c:forEach items="${address}" var="addr" varStatus="roll">
                <input type="radio" name="address" value="${addr.address}" id="address" checked> ${addr.address}<br>
            </c:forEach><br><br>
            <input type="text" name="total" id="total" hidden value="<c:out value=" ${total}"></c:out>">
            Or Add address: <input type="button" onclick="window.location.href='address';" value="addressess"><br><br><br>
            <input type="submit" value="pay" id="pay">
            <input type="button" onclick="window.location.href='web';" value="Cancel">
        </form>
    </h3>
</body>

</html>