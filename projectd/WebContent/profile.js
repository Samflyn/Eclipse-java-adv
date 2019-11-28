$(document).ready(function () {
    $("button").click(function (x) {
        x.preventDefault();
        var name = $(this).attr("value");
        $("#content").load("update.jsp?name=" + name);
    });
});