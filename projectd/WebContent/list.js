$(document).ready(function () {
    $("button").click(function () {
        var name = $(this).attr("value");
        var page = $(this).attr("id");
        if (page == "delete") {
            $.ajax({
                url: page,
                type: "post",
                data: { name },
                cache: false,
                success: function (x) {
                	$("#content").load("list.jsp");
            	//    $("#message").html(x).slideDown('slow');
                }
            });
        } else {
        	$("#content").load("update.jsp?name=" + name);
        }
    });
});