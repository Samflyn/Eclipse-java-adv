<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Web Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">$("#add").click( function() {
    var value = parseInt($(".myspan").text(), 10) + 1;
    $(".myspan").text(value);
});
$("#minus").click( function() {
    var value = parseInt($(".myspan").text(), 10) - 1;
    $(".myspan").text(value);    
});</script>
</head>
<body>
<button id="minus">Add -1</button>
<span class="myspan">1</span>
<button id="add">Add +1</button>
</body>
</html>