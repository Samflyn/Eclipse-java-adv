<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Task Status</title>
</head>

<body>
    <%String task = (String) request.getParameter("task");
if(task!=null){
%>
    <br>
    <h1 style="text-align: center;">Assigned task</h1>
    <hr><br><br>
    <div style="text-align: center;">
        To Employee : ${employee}<br><br>
        Assigned Task : <br>
        <textarea name="task" cols="100" rows="10" disabled>${task}</textarea>
    </div>
    <br><br>
    <div style="text-align: center;">
        Status : <br>
        <textarea name="status" cols="100" rows="10" disabled>${status}</textarea>
    </div>
    <%}else{ %>
    <br>
    <h1 style="text-align: center;">No tasks assigned!</h1>
    <%} %>
</body>

</html>