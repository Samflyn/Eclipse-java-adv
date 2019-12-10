<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Status</title>
</head>
<body>
<%
String task = (String) request.getAttribute("task");
if(task != null){ %>
<br>
    <h1 style="text-align: center;">Your Assigned task</h1>
    <hr><br><br>
    <div style="text-align: center;">
    <textarea name="status" id="status" cols="100" rows="10" disabled><%=task%></textarea>
    </div>
    <br>
    <h3 style="text-align: center;">Your Status</h3>
    <div style="text-align: center;">
        <textarea name="status" id="status" cols="100" rows="10"></textarea>
        </div>
    <h3 style="text-align: center;">
        Update Status : <input type="button" onclick="window.location.href='login.jsp';" value="update">
    </h3>
    <%} else {%>
    <br><br><br><br>
    <h1 style="text-align: center;">Your Have no tasks assigned</h1>
    <hr>
    <%} %>
</body>
</html>