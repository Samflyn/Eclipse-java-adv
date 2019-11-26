<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Users</title>
</head>
<body>
<div>
	<h2 style="text-align: center;">All User List</h2>
	<table style="width: 100%">
		<tr>
			<th>Name</th>
			<th>Password</th>
			<th>Date Of Birth</th>
			<th>Gender</th>
			<th>Department</th>
			<th>Role</th>
		</tr>
		<%!ResultSet rs;
		Connection con;%>
		<%{
			try {
				ServletContext context = getServletContext();
				String Driver = context.getInitParameter("dbDriver");
				String Host = context.getInitParameter("dbHost");
				String port = context.getInitParameter("dbport");
				String Uid = context.getInitParameter("dbUid");
				String password = context.getInitParameter("dbpassword");
				String sid = context.getInitParameter("dbsid");
				Class.forName(Driver);
				String url = "jdbc:oracle:thin:@" + Host + ":" + port + ":" + sid;
				con = DriverManager.getConnection(url, Uid, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			try {
				Statement st = con.createStatement();
				rs = st.executeQuery("select * from employees");
				while(rs.next()){
		%>
		<tr>
		<td><%out.print(rs.getString(1));%></td>
		<td><%out.print(rs.getString(2));%></td>
		<td><%out.print(rs.getString(3));%></td>
		<td><%out.print(rs.getString(4));%></td>
		<td><%out.print(rs.getString(5));%></td>
		<td><%out.print(rs.getString(6));%></td>
		<td> <a href="edit?action=edit&name=<%=rs.getString(1)%>">Update</a></td>
		<td> <a href="delete?action=delete&name=<%=rs.getString(1)%>">Delete</a></td>
		</tr>
		<%} } catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 style=\"color: red;\">Server Busy!</h1>");
		}%>
	</table>
	</div>
</body>
</html>