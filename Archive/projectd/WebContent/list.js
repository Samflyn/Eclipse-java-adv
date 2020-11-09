$(document).ready(function () {
    $("button").click(function () {
        var name = $(this).attr("value");
        var page = $(this).attr("id");
        if (page == "delete") {
            var r = confirm("Are you sure to delete : " + name);
            if (r == true) {
                $.ajax({
                    url: page,
                    type: "post",
                    data: { name },
                    cache: false,
                    success: function (x) {
                        $("#body").load("list.jsp");
                        alert(x);
                        // $("#message").html(x).slideDown('slow');
                    }
                });
            }
        } else {
            $("#content").load("update.jsp?name=" + name);
        }
    });
});