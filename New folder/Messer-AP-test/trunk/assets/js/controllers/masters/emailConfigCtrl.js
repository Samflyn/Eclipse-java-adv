'use strict';
/** 
  * controller for User Profile Example
*/
app.controller('emailConfigCtrl', ['$scope', '$q', '$rootScope', '$cookies', '$http', '$timeout', '$compile', '$window', 'DTOptionsBuilder', 'DTColumnBuilder', EmailConfigCtrl]);
function EmailConfigCtrl($scope, $q, $rootScope, $cookies, $http, $timeout, $compile, $window, DTOptionsBuilder, DTColumnBuilder) {
	$scope.data = {};
	var vm = this;
	vm.singleRecord = {};
	$scope.dtInstance = {};
	var serialNo = 1;

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

				$scope.loginDetails = {};
				$scope.loginDetails.userId = $cookies.get('userId');
				$scope.loginDetails.tenantId = $cookies.get('tenantId');
				$scope.loginDetails.tenantType = $cookies.get('tenantType');

				$http({
					method: 'POST',
					url: $rootScope.ctx + '/saveMailConfigs',
					data: $scope.data,
					headers: { 'Content-Type': 'application/json', 'loginDetails': JSON.stringify($scope.loginDetails) }
				}).then(function onSuccess(response) {
					$("#saveMsg").show().html('<div class="alert alert-success"<strong>Email Configuration saved successfully</strong> </div>').fadeOut(3000);
					$scope.isDirty = false;
					vm.reloadEmailConfigData();
					$scope.clear();
				}, function (err) {
					$("#saveMsg")
						.show()
						.html('<div class="alert alert-success"<strong>Failed to save Email Configuration</strong> </div>')
						.fadeOut(3000);
				});
				form.$setPristine(true);
			}
		}
	};

	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
		var defer = $q.defer();
		$http.get($rootScope.ctx + '/getAllEmailConfigs').then(function (result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('createdRow', createdRow);
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}

	vm.displayTable = true;
	vm.dtColumns = [
		DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
		DTColumnBuilder.newColumn('host').withTitle('Mail Host'),
		DTColumnBuilder.newColumn('port').withTitle('Mail Port'),
		DTColumnBuilder.newColumn('userName').withTitle('Username'),
		DTColumnBuilder.newColumn('tenantName').withTitle('Company Name'),
		DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable().renderWith(actionsHtml)
	];

	function serialNoHtml() {
		return serialNo++;
	}

	function actionsHtml(data, type, full, meta) {
		vm.singleRecord[full.mailConfigId] = full;
		return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="emailCtrl.editEmailConfig(emailCtrl.singleRecord[' + full.mailConfigId + '])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="emailCtrl.deleteEmailConfigById(emailCtrl.singleRecord[' + full.mailConfigId + '])"></button></span>';
	}

	vm.editEmailConfig = function (singleObj) {
		$scope.data = singleObj;
	}

	$scope.clear = function () {
		$scope.data = {};
	}

	vm.deleteEmailConfigById = function (singleObj) {
		swal({
			title: "",
			text: "Are you sure want to delete",
			showCancelButton: true,
			confirmButtonColor: "#8B0000",
			confirmButtonText: "Yes",
			closeOnConfirm: true
		}, function (isConfirm) {
			$http({
				method: 'DELETE',
				url: $rootScope.ctx + '/deleteEmailConfig/' + singleObj.mailConfigId
			}).then(function onSuccess(response) {
				vm.reloadEmailConfigData();
				$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + 'Email Configuration deleted successfully' + '</strong> </div>').fadeOut(3000);
			}, function (err) {
				$("#saveMsg").show().html('<div class="alert alert-success"<strong>Failed to delete Email Configuration</strong> </div>').fadeOut(3000);
			})
		})
	}

	vm.reloadEmailConfigData = function () {
		$scope.dtInstance.reloadData(function () {
			var defer = $q.defer();
			$http.get($rootScope.ctx + '/getAllEmailConfigs').then(
				function (result) {
					defer.resolve(result.data);
				});
			return defer.promise;
		}, true);
	}

	function getCompany() {
		$http({
			method: 'GET',
			url: $rootScope.ctx + '/company/getAll',
			async: false,
		}).then(function (response) {
			$scope.customers = response.data;
		}, function (error) {
			console.log(error, 'can not get roles.');
		});
	}

	getCompany();
}
