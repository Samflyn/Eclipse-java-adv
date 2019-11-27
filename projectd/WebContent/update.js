$(document).ready(function () {
	$('#submit').click(function () {
		e.preventDefault();
		var name = $("#name").val();
		var password = $("#password").val();
		var date = $("#date").val();
		var gender = $("#gender").val();
		var dept = $("#dept").val();
		var role = $("#role").val();
		$.ajax({
			url: "update",
			type: "post",
			data: {
				name, password, date, gender, dept, role
			},
			cache: false,
			success: function (x) {
				$("#message").html(x);
			}
		});
	});
});