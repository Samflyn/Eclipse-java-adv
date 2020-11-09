$(document).ready(function () {
	$("ul#sidebar li a").click(function () {
		var page = $(this).attr("href");
		$("#content").load(page + ".jsp");
		if (page != "logout") {
			return false;
		}
	});
});
