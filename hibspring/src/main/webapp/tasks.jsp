<%@page import="com.test.bean.SamEmployees"%>
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
    <title>Tasks</title>
</head>

<body>

    <%
    session = request.getSession(false);
String name = (String) session.getAttribute("name");
String task = null;
String status = null;
try{
	SessionFactory sf = HibCon.getSessionFactory();
	Session s = sf.openSession();
	Query query = s.createQuery("from SamEmployees s where s.name = :name");
	query.setParameter("name", name);
	SamEmployees se = (SamEmployees) query.uniqueResult();
	task = se.getTask();
	status = se.getStatus();
}catch(Exception e){
	e.printStackTrace();
}
if(task != null){ %>
    <form action="tasks" method="post">
    <br>
    <h1 style="text-align: center;">Your Assigned task</h1>
    <hr>
    <h3 style="text-align: center; color: red;">${fail}</h3>
    <h3 style="text-align: center; color: green;">${success}</h3>
    <br><br>
    <div style="text-align: center;">
        <textarea name="task" id="task" cols="100" rows="10" disabled><%=task%></textarea>
    </div>
    <br>
    <h3 style="text-align: center; color: red;">Your Status</h3>
    <div style="text-align: center;">
        <textarea name="status_value" id="status" cols="100" rows="10"><%if(status!=null){ %><%=status %><%} %></textarea>
    </div>
    <h3 style="text-align: center;">
        Update Status : <input type="submit" value="Submit">
    </h3>
    </form>
    <%} else {%>
    <br><br><br><br>
    <h1 style="text-align: center;">Your Have no tasks assigned</h1>
    <hr>
    <%} %>
</form>
</body>

</html>