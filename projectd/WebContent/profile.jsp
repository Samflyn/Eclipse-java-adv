<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
</head>
<body>
<%session=request.getSession(false);
String name = (String) session.getAttribute("name");
out.print(name);
%>
</body>
</html>