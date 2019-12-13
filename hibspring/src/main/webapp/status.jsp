<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.test.bean.SamEmployees"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Task Status</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
        $(document).ready(function () {
            $("button").click(function () {
                var name = $(this).attr("value");
                var r = confirm("Are you sure to remove task for " + name);
                if (r == true) {
                    $.ajax({
                        url: "removetask",
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
	<br>
	<h1 style="text-align: center;">Assigned task</h1>
	<hr>
	<h3 style="text-align: center;">${ok }</h3>
	<%
		List<SamEmployees> list = (List) request.getAttribute("result");
		if (list != null) {
			Iterator<SamEmployees> itr = list.iterator();
			while (itr.hasNext()) {
				SamEmployees next = itr.next();
				String name = next.getName();
				String task = next.getTask();
				String status = next.getStatus();
				if (status == null) {
					status = "No status updated yet!";
				}
	%>
	<div style="text-align: center;">
		To Employee :
		<%=name%><br> <br> Assigned Task : <br>
		<textarea name="task" cols="100" rows="7" disabled><%=task%></textarea>
	</div>
	<br>
	<br>
	<div style="text-align: center;">
		Status : <br>
		<textarea name="status" cols="100" rows="7" disabled><%=status%></textarea>
	</div>
	<br>
	<div style="text-align: center;">
		<button id="remove" value="<%=name%>">remove task</button>
	</div>
	<br>
	<hr>
	<br>
	<%
		}
		}
	%>
</body>

</html>