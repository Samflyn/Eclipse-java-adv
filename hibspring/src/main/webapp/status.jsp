<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Task Status</title>
</head>

<body>
   
    <br>
    <h1 style="text-align: center;">Assigned task</h1>
    <hr><br><br>
    <h3 style="text-align: center;">${ok }</h3>
    <div style="text-align: center;">
        To Employee : ${employee}<br><br>
        Assigned Task : <br>
        <textarea name="task" cols="100" rows="10" disabled>${task}</textarea>
    </div>
    <br><br>
    <div style="text-align: center;">
        Status : <br>
        <textarea name="status" cols="100" rows="10" disabled>${status_value}</textarea>
    </div>
    <br>
    <h1 style="text-align: center;">No tasks assigned!</h1>
</body>

</html>