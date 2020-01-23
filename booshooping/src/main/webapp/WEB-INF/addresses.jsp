<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Address</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#submit").click(function () {
                var address = $.trim($("#address").val());
                if (address != "") {
                    $.ajax({
                        url: "addaddress",
                        type: "post",
                        data: {
                            address
                        }
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                } else {
                    alert("Please enter an address!");
                    return false;
                }
            });
            $("#remove1,#remove2,#remove3,#remove4,#remove5").click(function () {
                var vaa = this.id;
                var pid = vaa.replace("remove", "");
                var id = $("#id" + pid).val();
                $.ajax({
                    url: "removeaddress",
                    type: "post",
                    data: {
                        id
                    }
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
            });
        });
    </script>
</head>

<body>
    <h1 style="text-align: center;">Addressess</h1>
    <hr>
    <table style="width: 100%" id="product">
        <tr>
            <th>Id</th>
            <th>Address</th>
        </tr>
        <c:if test="${not empty address}">
            <c:forEach items="${address}" var="addr" varStatus="roll">
                <tr>
                    <td style="text-align: center;">${roll.count}</td>
                    <input type="text" name="id" id="id${roll.count}" value="${addr.id}" hidden>
                    <td style="text-align: center;" id="address${roll.count}">${addr.address}</td>
                    <td style="text-align: center;"><input type="button" value="remove" id="remove${roll.count}"></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <br>
    <br>
    <div style="text-align: center;">
        <h3>Or add a new address</h3>
        <textarea id="address" cols="100" rows="10"></textarea>
        <br> <br> <input type="submit" value="submit" id="submit">
        <input type="reset" value="clear">
    </div>
</body>

</html>