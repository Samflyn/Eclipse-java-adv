<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.durga.user.model.UserBean"%>
<%@ page import="com.durg.user.dao.UserDao"%>
<%@ page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All User</title>
</head>
<body>
<%
UserDao dao = new UserDao();
List<UserBean> userList = dao.getAllUsers();

%>
<table border="1">
<tr>
<th>User Id</th>
<th>First Name</th>
<th>Middle Name</th>
<th>Last Name</th>
<th>Gender</th>
<th>Zip Code</th>
</tr>
<tr>
<%

for (UserBean user : userList) {
%>
<td><%=user.getUserId()%></td>
<td><%=user.getUserFirstName()%></td>
<td><%=user.getUserMiddleName()%></td>
<td><%=user.getUserLastName()%></td>
<td><%=user.getUserGender()%></td>
<td><%=user.getUserZipCode()%></td>
<td> <a href="userHandler?action=editform&userId=<%=user.getUserId()%>">Update</a></td>
<td> <a href="userHandler?action=delete&userId=<%=user.getUserId()%>">Delete</a></td>
</tr>
<%
}

%>
</table>
<p><a href="userHandler?action=insert">Add User</a></p>
</body>
</html>