$(document).ready(function () {
			$('#isubmit').click(function (e) {
				e.preventDefault();
				var name = "name=" + $("#name").val();
				var password = "password=" + $("#password").val();
				var date = "date=" + $("#date").val();
				var gender = "gender=" + $("#gender").val();
				var dept = "dept=" + $("#dept").val();
				var role = "role=" + $("#role").val();

				$.ajax({
					url: "register",
					type: "post",
					data: {
						name, password, date, gender, dept, role
					},
					cache: false,
					success: function (register) {
						$("#message").html(register).slideDown('slow');
					}
				});
			});
		});