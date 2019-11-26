$(document).ready(function () {
			$('#submit').click(function (e) {
				e.preventDefault();
				var name = $("#name").val();
				var password = $("#password").val();
				var rpassword = $("#rpassword").val();
				var date = $("#date").val();
				var gender = $("#gender").val();
				var dept = $("#dept").val();
				var role = $("#role").val();

				$.ajax({
					url: "register",
					type: "post",
					data: {
						name, password, rpassword, date, gender, dept, role
					},
					cache: false,
					success: function (register) {
						$("#message").html(register).slideDown('slow');
					}
				});
			});
		});