$(document).ready(function () {
    $("button").click(function (e) {
        var name = $(this).attr("value");
        var page = $(this).attr("id");
        var test = "testing";
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
        }
        $("#content").load("update.jsp?name=" + name);
    });
});