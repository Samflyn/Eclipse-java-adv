'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('manageCustomerUserCtrl', ['$q', '$cookies', '$compile', '$filter', 'DTOptionsBuilder', 'UserService', 'DTColumnBuilder', '$scope', '$http', '$window', '$rootScope', function ($q, $cookies, $compile, $filter, DTOptionsBuilder, UserService, DTColumnBuilder, $scope, $http, $window, $rootScope) {
	$scope.data = {};
	$scope.data.companyDto = {};
	$scope.data.roleMasterDto = {};
	$scope.data.countryMasterDto = {};
	$scope.data.stateDto = {};
	var vm = this;
	vm.singleRecord = {};
	$scope.dtInstance = {};
	var serialNo = 1;
	$scope.roleName = $cookies.get('roleName');
	$scope.form = {
		submit: function (form) {
			var firstError = null;
			if (form.$invalid) {
				var field = null;
				for (field in form) {
					if (field[0] != '$') {
						if (firstError === null && !form[field].$valid) {
							firstError = form[field].$name;
						}
						if (form[field].$pristine) {
							form[field].$dirty = true;
						}
					}
				}
				angular.element('.ng-invalid[name=' + firstError + ']').focus();
				return;
			} else {
				$("#saveMsg").html("");
				UserService.saveUser($scope.data).then(function (response) {
					$scope.clear();
					$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + response.data.message + '</strong> </div>').fadeOut(5000);
					vm.reloadUserData();
				}).catch(function (response) {
					$("#saveMsg").show().html('<div class="alert alert-danger"<strong>' + response.data.errorMessage + '</strong> </div>').fadeOut(5000);
				});
				form.$setPristine(true);
			}
		}
	};


	$scope.clear = function () {
		$scope.data = {};
	}

	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
		var defer = $q.defer();
		$http.get($rootScope.ctx + '/user/getAll?userId=' + $rootScope.user.userId).then(function (result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('createdRow',
		createdRow);
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}

	vm.dtColumns = [
		DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
		DTColumnBuilder.newColumn('username').withTitle('Username'),
		DTColumnBuilder.newColumn('emailId').withTitle('Email Id'),
		DTColumnBuilder.newColumn('companyDto.companyName').withTitle('Company Name'),
		DTColumnBuilder.newColumn('roleMasterDto.roleName').withTitle('Role'),
		DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable().renderWith(actionsHtml)
	];

	function serialNoHtml() {
		return serialNo++;
	}

	function actionsHtml(data, type, full, meta) {
		vm.singleRecord[full.userId] = full;
		return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="customerUserCtrl.editUser(customerUserCtrl.singleRecord[' + full.userId + '])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="customerUserCtrl.deleteUserById(customerUserCtrl.singleRecord[' + full.userId + '])"></button></span>';
	}
	vm.editUser = function (singleObj) {
		$scope.data = singleObj;
		$scope.data['pic'] = singleObj.pic;
	}

	vm.deleteUserById = function (singleObj) {
		swal({
			title: "",
			text: "Are you sure want to delete",
			showCancelButton: true,
			confirmButtonColor: "#8B0000",
			confirmButtonText: "Yes",
			closeOnConfirm: true
		}, function (isConfirm) {
			if (isConfirm) {
				$http({
					method: 'DELETE',
					url: $rootScope.ctx + '/deleteUser/' + singleObj.userId
				}).then(function onSuccess(response) {
					vm.reloadUserData();
					$("#saveMsg")
						.show()
						.html('<div class="alert alert-success"<strong>' + 'deleted successfully' + '</strong> </div>')
						.fadeOut(3000);

				}, function (err) {

					$("#saveMsg")
						.show()
						.html('<div class="alert alert-success"<strong>Failed</strong> </div>')
						.fadeOut(3000);
				})
			}
		});
	}
	vm.reloadUserData = function () {
		$scope.dtInstance.reloadData(function () {
			var defer = $q.defer();
			$http.get($rootScope.ctx + '/user/getAll?userId=' + userId).then(
				function (result) {
					defer.resolve(result.data);
				});
			return defer.promise;
		}, true);
	}

	$scope.uploadImage = function (input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$scope.data['pic'] = e.target.result.split(",")[1]
			}
			reader.readAsDataURL(input.files[0]);
		}

	}
	$scope.getManager = function (tenantId) {
		$http({
			method: 'GET',
			url: $rootScope.ctx + '/getAllManager',
			params: { "tenantId": tenantId }
		}).then(function (response) {
			$scope.managerList = response.data;
		}, function (error) {
			console.log(error, 'can not get Country Details.');
		});
	}


	//get states
	function getState() {
		$http({
			method: 'get',
			url: $rootScope.ctx + '/getAllStates',
			async: false,
		}).then(function (response) {
			$scope.states = response.data;
		}, function (error) {
			console.log(error, 'can not get states.');
		});
	}


	//get countries
	function getCountry() {
		$http({
			method: 'GET',
			url: $rootScope.ctx + '/getAllCountries',
			async: false,
		}).then(function (response) {
			$scope.countries = response.data;
		}, function (error) {
			console.log(error, 'can not get states.');
		});
	}


	//get roles
	function getroles() {
		$http({
			method: 'GET',
			url: $rootScope.ctx + '/getAllUserRoles',
			async: false,
		}).then(function (response) {
			//$scope.roles=$filter('filter')(response.data, function(role){return role.roleName=='Accountant';});
			$scope.roles = response.data;
			//$scope.data.roleMasterDto.roleId=$scope.roles[0].roleId;
		}, function (error) {
			console.log(error, 'can not get roles.');
		});
	}

	function getAllCustomers() {
		$http({
			method: 'GET',
			url: $rootScope.ctx + '/company/getAll',
			async: false,
		}).then(function (response) {
			$scope.companyList = response.data;
		}, function (error) {
			console.log(error, 'can not get roles.');
		});
	}

	getCountry();
	getState();
	getroles();
	getAllCustomers();

}]);