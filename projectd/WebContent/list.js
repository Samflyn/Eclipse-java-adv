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
                    $("#message").html(x);
                }
            });
        } else {
        	$("#content").load("update.jsp?name=" + name);
        }
    });
});