<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.demo.project.ConnectionGen"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="list.js">
	</script>
</head>
<body>
<%!ResultSet rs;
	Connection con;%>
<%String name = (String) session.getAttribute("name"); 
ConnectionGen cont=new ConnectionGen();
try{
	con=cont.getConnection(getServletContext());
	Statement st=con.createStatement();
	String str = "select * from employees where name='"+name+"'";
	ResultSet rs=st.executeQuery(str);
	rs.next();
}catch(Exception e){
	e.printStackTrace();
}
%>
<div id="content">
<h1 style="text-align: center;">Profile Details</h1><hr>
<br>Name : <%=rs.getString(1) %> <br>
<br>Date Of Birth : <%=rs.getString(3) %> <br>
<br>Gender : <%=rs.getString(4) %> <br>
<br>Department : <%=rs.getString(5) %> <br>
<br>Role : <%=rs.getString(6) %> <br>
<br><br><button id="update" value="<%=out.print(rs.getString(1))%>">update</button>
</div>
</body>
</html>