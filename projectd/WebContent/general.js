$(document).ready(function() {
	// $("#content").load("Home.jsp");

	$("ul#sidebar li a").click(function(e) {
		var page = $(this).attr("href");
		$("#content").load(page + ".jsp");
		if(page!="logout"){
			return false;
		}
	});
});
