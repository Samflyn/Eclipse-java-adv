<%@page import="com.demo.project.DataBaseCon"%>
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
		<th>Emp No</th>
			<th>Name</th>
			<th>Date Of Birth</th>
			<th>Gender</th>
			<th>Department</th>
			<th>Role</th>
		</tr>
		<%!ResultSet rs;
		Connection con;%>
		<%
			try {
				DataBaseCon db =new DataBaseCon();
				con = db.getConn();
				Statement st = con.createStatement();
				rs = st.executeQuery("select * from employees");
				while(rs.next()){
		%>
		<tr>
		<td><%out.print(rs.getInt(1));%></td>
		<td><%out.print(rs.getString(2));%></td>
		<td><%out.print(rs.getString(4));%></td>
		<td><%out.print(rs.getString(5));%></td>
		<td><%out.print(rs.getString(6));%></td>
		<td><%out.print(rs.getString(7));%></td>
		</tr>
		<%} } catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 style=\"color: red;\">Server Busy!</h1>");
			try {
				con.rollback();
			} catch (Exception p) {
				p.printStackTrace();
			}
		}%>
	</table>
	</div>
</body>
</html>