<%@page import="com.test.bean.SamEmployees"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.query.Query"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.test.conn.HibCon"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>

<body>
    <%session = (HttpSession) request.getAttribute("session");
String name = (String) session.getAttribute("name");
%>
    <form action="createtask" method="post">
        <br>
        <h1 style="text-align: center;">Assign a task</h1>
        <hr>
        <h3 style="text-align: center; color: red;">${fail}</h3>
        <h3 style="text-align: center; color: green;">${success}</h3>
        <br><br>
        <div style="text-align: center;">
            Description : <br>
            <textarea name="task" id="task" cols="100" rows="10"></textarea>
        </div>
        <br><br>
        <div style="text-align: center;">
            Assign to employee :<select name="employee">
                <%SessionFactory sf = HibCon.getSessionFactory();
			Session s = sf.openSession();
			Query query = s.createQuery("from SamEmployees");
			List list = query.list();
			Iterator itr = list.iterator();
			while(itr.hasNext()){
				SamEmployees se = (SamEmployees) itr.next();
				if(se.getRole().equals("Employee")){
			%>
                <option value=<%=se.getId() %>><%=se.getName() %></option>
                <%} }%>
        </div>
        <br>
        <input type="text" name="assignedby" value="<%=name %>" hidden>
        <h3 style="text-align: center;">
            Assign : <button type="submit">assign</button>
        </h3>
    </form>
</body>

</html>