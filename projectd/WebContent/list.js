$(document).ready(function () {
    $("button").click(function (e) {
        var name = $(this).attr("value");
        var page = $(this).attr("id");
        if (page == "delete") {
            $.ajax({
                url: page,
                type: "post",
                data: { name },
                cache: false,
                success: function (register) {
                    $("#message").html(register).slideDown('slow');
                }
            });
        } else {
        	$("#content").load("update.jsp?name=" + name);
        }
    });
});