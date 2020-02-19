'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('manageDepartmentCtrl', ['$q', '$cookies', '$compile', '$filter', 'DTOptionsBuilder', 'DepartmentService', 'DTColumnBuilder', '$scope', '$http', '$window', '$rootScope', function ($q, $cookies, $compile, $filter, DTOptionsBuilder, DepartmentService, DTColumnBuilder, $scope, $http, $window, $rootScope) {
	$scope.data = {};
	$scope.data.deptCode = {};
	$scope.data.deptName = {};
	$scope.data.dimensionType = {};
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
				DepartmentService.saveDepartment($scope.data).then(function (response) {
					$scope.clear();
					$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + response.data.message + '</strong> </div>').fadeOut(5000);
					vm.reloadDepartmentData();
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

	// vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
	// 	var defer = $q.defer();
	// 	$http.get($rootScope.ctx + '/user/getAll?deptId=' + $rootScope.department.deptId).then(function (result) {
	// 		defer.resolve(result.data);
	// 	});
	// 	return defer.promise;
	// }).withPaginationType('full_numbers').withOption('createdRow',
	// 	createdRow);
	// function createdRow(row, data, dataIndex) {
	// 	$compile(angular.element(row).contents())($scope);
	// }

	vm.dtColumns = [
		DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
		DTColumnBuilder.newColumn('deptId').withTitle('Department Id'),
		DTColumnBuilder.newColumn('deptCode').withTitle('Department Code'),
		DTColumnBuilder.newColumn('deptName').withTitle('Department Name'),
		DTColumnBuilder.newColumn('dimensionType').withTitle('Dimension Type'),
		DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable().renderWith(actionsHtml)
	];
	
	vm.reloadUserData = function () {
		$scope.dtInstance.reloadData(function () {
			var defer = $q.defer();
			$http.get($rootScope.ctx + '/user/getAll?deptId=' + deptId).then(
				function (result) {
					defer.resolve(result.data);
				});
			return defer.promise;
		}, true);
	}
}]);