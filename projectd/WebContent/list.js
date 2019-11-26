$(document).ready(function () {
    $("button").click(function (e) {
        alert($(this).attr("value"));
        var name = $(this).attr("value");
        var page = $(this).attr("id");
        if(page = "delete"){
        	$.ajax({
                url: page,
                type: "post",
                data: name,
                cache: false,
                success: function (register) {
                	$("#content").load("list.jsp");
                    $("#message").html(register).slideDown('slow');
                }
            });
        }
        $("#content").load("update.jsp");
    });
});