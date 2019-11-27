$(document).ready(function () {
    $("button").click(function () {
        e.preventDefault();
        var name = $(this).attr("value");
        $("#content").load("update.jsp?name=" + name);
    });
});