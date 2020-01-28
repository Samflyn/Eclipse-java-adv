<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Manage</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.remove').click(function () {
                var id = this.id;
                var i = id.replace("remove", "");
                var pid = $("#id" + i).text();
                var name = $("#name" + i).text();
                if (confirm('Are you sure you want to delete "' + name + '" ?')) {
                    $.ajax({
                        url: "delproduct",
                        type: "post",
                        data: {
                            pid
                        }
                    });
                    setTimeout(function () {
    					location.reload();
    				}, 500);
                }
            });
        });
    </script>
</head>

<body>
    <h1 style="text-align: center;">Product List</h1>
    <h1 style="text-align: end;">
        <input type="button" onclick="window.location.href='javascript:history.back()';" value="Go back">
    </h1>
    <table style="width: 100%" id="product">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>About</th>
            <th>Price</th>
            <th></th>
        </tr>
        <c:forEach items="${list}" var="product" varStatus="roll">
            <tr>
                <td style="text-align: center;" id="id${roll.count}">${product.id}</td>
                <td style="text-align: center;" id="name${roll.count}">${product.name}</td>
                <td style="text-align: center;">${product.about}</td>
                <td style="text-align: center;">${product.price}</td>
                <td style="text-align: center;"><input type="button" value="remove" id="remove${roll.count}"
                        class="remove"></td>
            </tr>
        </c:forEach>
    </table>
</body>

</html>