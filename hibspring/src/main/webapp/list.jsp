<%@page import="com.test.bean.SamEmployees"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.query.Query"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.test.conn.HibCon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>All Users</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$(document).ready(function () {
			$("button").click(function () {
				var name = $(this).attr("value");
				var r = confirm("Are you sure to delete : " + name);
				if (r == true) {
					$.ajax({
						url: "delete",
						type: "post",
						data: { name },
						success: function (x) {
							alert(x);
							window.location.reload(true);
						}
					});
				}
			});
		});
	</script>
</head>

<body>
	<div id="body">
		<%String name = (String) session.getAttribute("name"); %>
		<h2 style="text-align: center;">All User List</h2>
		<table style="width: 100%">
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Password</th>
				<th>Date Of Birth</th>
				<th>Role</th>
				<th>Manager</th>
			</tr>
			<%try {
					SessionFactory sf = HibCon.getSessionFactory();
					Session s = sf.openSession();
					Query query = s.createQuery("from SamEmployees");
					List list = query.list();
					Iterator itr = list.iterator();
				while(itr.hasNext()){ 
				SamEmployees se = (SamEmployees) itr.next();
				if(!se.getName().equals(name)){
				%>
			<tr>
				<td><%=se.getId()%></td>
				<td><%=se.getName()%></td>
				<td><%=se.getPassword()%></td>
				<td><%=se.getDob()%></td>
				<td><%=se.getRole()%></td>
				<td><%=se.getManager().getName()%></td>
				<td> <button id="delete" value="<%=se.getId()%>">delete</button></td>
			</tr>
			<%} }} catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 style=\"color: red;\">Server Busy!</h1>");
			}%>
		</table>
	</div>
</body>

</html>