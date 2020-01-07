$(document).ready(function () {
	$("button").click(function () {
		var id = $(this).attr("value");
		var page = $(this).attr("id");
		if (page == "delete") {
			var r = confirm("Are you sure to delete : " + id);
			if (r == true) {
				$.ajax({
					url: page,
					type: "post",
					data: { id },
					cache: false,
					success: function () {
						location.replace("list");
					},
					error: function () {
						location.replace("list");
					}
				});
			}
		} else {
			location.replace("update?id=" + id);
		}
	});
});