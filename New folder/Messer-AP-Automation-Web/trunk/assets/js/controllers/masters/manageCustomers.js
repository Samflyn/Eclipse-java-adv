'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('manageCustomerCtrl', ['DTOptionsBuilder', 'CompanyService', 'DTColumnBuilder', '$scope', '$cookies', '$http', '$window', '$q', '$compile', '$rootScope',
	function (DTOptionsBuilder, CompanyService, DTColumnBuilder, $scope, $cookies, $http, $window, $q, $compile, $rootScope) {
		$scope.tenant = {};
		$scope.exporttime = false;
		$scope.tenant.scheduleExport = false;
		$scope.tenant.countryConfiguration = 'INDIA';
		$scope.tenant.scheduleExpression = 'day';
		var vm = this;
		$scope.bulkPath = false;
		vm.singleRecord = {};
		$scope.dtInstance = {};
		$scope.tenantPrivilagesList = [];
		$scope.millsec;
		var serial = 1;
		$scope.tenant.countryMasterDto = {};
		$scope.tenant.stateMasterDto = {};
		var tenantpre = ['MastermanageCustomerUser', 'MastermanageVendor', 'MastermanageVendorUser', 'MastermanageItemMaster', 'MastermanageuqcMaster', 'MastermanageItemMappingMaster', 'MastermanageuqcMappingMaster', 'generateInvoice', 'invoiceQueue', 'bulkProcessedQueue', 'bulkFileStatusQueue', 'bulkFileUpload', 'reportInvoicereport', 'reportBulkInvoicereport', 'reportPaymentDuereport', 'statusCounts', 'apiCalls', 'yearlyReport', 'notifications'];
		$scope.scheduletime = function () {
			if ($scope.tenant.scheduleExport) {
				$scope.exporttime = true;
			} else {
				$scope.exporttime = false;
			}

		}
		$('#scheduleTime').datetimepicker({
			format: 'HH:mm:ss',
			maxDate: moment("24:00:00", "HH:mm:ss"),
			minDate: moment("01:00:00", "HH:mm:ss")
		}).datetimepicker().on('dp.change', function (ev) {
			$scope.tenant.scheduleTime = $('#scheduleTime').val();
			var millsplit = $scope.tenant.scheduleTime.split(":");
			$scope.millsec = ((parseInt(millsplit[0]) * 3600) + (parseInt(millsplit[1]) * 60) + (parseInt(millsplit[2]))) * 1000;
			console.log($scope.millsec)
		});

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
					$scope.saveData();
					form.$setPristine(true);
				}
			}

		};
		$scope.saveData = function () {
			CompanyService.save($scope.tenant).then(function (response) {
				vm.reloadtenantData();
				$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + response.data.message + '</strong> </div>').fadeOut(5000);
				$scope.clear();
			}).catch(function (response) {
				$("#saveMsg").show().html('<div class="alert alert-danger"<strong>' + response.data.errorMessage + '</strong> </div>').fadeOut(5000);
			});
		}

		$scope.updTenantPrivilages = function (value) {
			var index = -1;
			if (value) {
				if ($scope.tenantPrivilagesList) {
					index = $scope.tenantPrivilagesList.indexOf(value);
				} else {
					$scope.tenantPrivilagesList = [];
				}
				if (index > -1) {
					$scope.tenantPrivilagesList.splice(index, 1);
				} else {
					$scope.tenantPrivilagesList.push(value);
				}
				$scope.tenant.tenantPrivileges = $scope.tenantPrivilagesList.toString();
			}
		}

		$scope.clear = function () {
			$("#pic").replaceWith($("#pic").val('').clone(true));
			$scope.tenant = {};
			$scope.millsec = "";
			$scope.exporttime = false;
			$scope.tenant.scheduleExport = false;
		}

		$scope.getAllStates = function () {
			$http({
				method: 'GET',
				url: $rootScope.ctx + '/getAllStates'
			}).then(function (response) {
				$scope.state = response.data;
			}, function (error) {
				console.log(error, 'can not get state list.');
			});
		}

		$scope.getAllCountries = function () {
			$http({
				method: 'GET',
				url: $rootScope.ctx + '/getAllCountries'
			}).then(function (response) {
				$scope.country = response.data;
			}, function (error) {
				console.log(error, 'can not get Country Details.');
			});
		}

		vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
			var defer = $q.defer();
			$http.get($rootScope.ctx + '/company/getAll').then(function (result) {
				defer.resolve(result.data);
			});
			return defer.promise;
		}).withPaginationType('full_numbers').withOption('createdRow', createdRow);
		function createdRow(row, data, dataIndex) {
			$compile(angular.element(row).contents())($scope);
		}

		vm.dtColumns = [
			DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
			DTColumnBuilder.newColumn('companyCode').withTitle('Company Code'),
			DTColumnBuilder.newColumn('companyName').withTitle('Company Name'),
			DTColumnBuilder.newColumn('registredId').withTitle('Registered Id'),
			DTColumnBuilder.newColumn('emailId').withTitle('Email'),
			DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable().renderWith(actionsHtml)

		];
		function serialNoHtml() {
			return serial++;
		}

		function actionsHtml(data, type, full, meta) {
			vm.singleRecord[full.companyId] = full;
			return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="tenantCtrl.edittenantdata(tenantCtrl.singleRecord[' + full.companyId + '])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="tenantCtrl.deletetenantbyid(tenantCtrl.singleRecord[' + full.companyId + '])"></button></span>';
		}
		vm.edittenantdata = function (singleObj) {
			$scope.tenant = singleObj;
		}

		vm.deletetenantbyid = function (singleObj) {
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
						url: $rootScope.ctx + '/company/delete/' + singleObj.companyId
					}).then(function onSuccess(response) {
						vm.reloadtenantData();
						$("#saveMsg")
							.show()
							.html('<div class="alert alert-success"<strong>' + response.data.message + '</strong> </div>')
							.fadeOut(3000);

					}, function (err) {
						$("#saveMsg")
							.show()
							.html('<div class="alert alert-success"<strong>Failed to create company</strong> </div>')
							.fadeOut(3000);
					})
				}
			});
		}

		vm.reloadtenantData = function () {
			$scope.dtInstance.reloadData(function () {
				var defer = $q.defer();
				$http.get($rootScope.ctx + '/company/getAll').then(
					function (result) {
						defer.resolve(result.data);
					});
				return defer.promise;
			}, true);
		}

		$scope.getAllStates();
		$scope.getAllCountries();

	}]);