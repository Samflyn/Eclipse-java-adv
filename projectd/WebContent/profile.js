$(document).ready(function () {
    $("button").click(function (e) {
        e.preventDefault();
        var name = $(this).attr("value");
        $("#content").load("update.jsp");
    });
});