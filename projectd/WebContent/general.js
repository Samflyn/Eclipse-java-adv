$(document).ready(function() {
	// $("#content").load("Home.jsp");

	$("ul#sidebar li a").click(function() {
		var page = $(this).attr("href");
		$("#content").load(page + ".jsp");
		return false;
	});
});