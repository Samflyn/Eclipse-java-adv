<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.durga.user.model.UserBean"%>
<%@ page import="com.durg.user.dao.UserDao"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit User</title>
</head>
<body>
<%
UserBean user = new UserBean();
%>
<%
UserDao dao = new UserDao();
%>
<form method="POST" action='userHandler' name="frmEditUser">
<input type="hidden" name="action" value="edit" /> 

<%
String userId = request.getParameter("userId");
if (!((userId) == null)) {
user = dao.getUserById(userId);
%>

<table>
<tr>
<td>User ID</td>
<td><input type="text" name="userId" readonly="readonly"
value="<%=user.getUserId()%>"></td>
</tr>
<tr>
<td>First Name</td>
<td><input type="text" name="firstName" value="<%=user.getUserFirstName()%>" /></td>
</tr>
<tr>
<td>Middle Name</td>
<td><input type="text" name="middleName" value="<%=user.getUserMiddleName()%>"/></td>
</tr>
<tr>
<td>Last Name</td>
<td><input type="text" name="lastName" value="<%=user.getUserLastName()%>"/></td>
</tr>
<tr>
<td>Gender</td>
<td><input type="text" name="gender" value="<%=user.getUserGender()%>" /></td>
</tr>
<tr>
<td>Zip Code</td>
<td><input type="text" name="zip" value="<%=user.getUserZipCode()%>"/></td>
</tr>
<tr>
<td><input type="submit" value="Update" /></td>
</tr>
</table>
<%
} else
out.println("ID Not Found");
%>
</form>
</body>
</html>