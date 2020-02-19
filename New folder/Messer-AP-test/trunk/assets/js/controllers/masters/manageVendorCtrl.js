'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('manageVendorCtrl', ['DTOptionsBuilder', 'TenantService', 'DTColumnBuilder', '$scope', '$cookies', '$http', '$window', '$q', '$compile', '$rootScope',
	function (DTOptionsBuilder, TenantService, DTColumnBuilder, $scope, $cookies, $http, $window, $q, $compile, $rootScope) {
		$scope.tenant = {};
		var vm = this;
		vm.singleRecord = {};
		$scope.dtInstance = {};
		var serial = 1;
		$scope.tenant.countryMasterDto = {};
		$scope.tenant.stateMasterDto = {};
		$scope.tenantPrivilagesList = [];
		var tenantpre = ['MastermanageVendorUser', 'MastermanageItemMapping', 'MastermanageuqcMapping', 'generateInvoice', 'invoiceQueue', 'reportInvoicereport', 'reportPaymentDuereport'];

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
					$scope.tenant.parentTenantId = $scope.loginDetails.tenantId;
					$scope.tenant.tenantType = 'VENDOR';
					TenantService.saveTenant($scope.tenant, $scope.loginDetails).then(function (response) {
						$scope.isDirty = false;
						vm.reloadtenantData();
						for (var i = 0; i < tenantpre.length; i++) {
							console.log($scope[tenantpre[i]])
							$scope[tenantpre[i]] = false;
							$scope['value' + i] = false;
						}
						$scope.tenant.tenantPrivilagesList = [];;
						$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + response.data.message + '</strong> </div>').fadeOut(5000);
						$scope.clear();
					})
						.catch(function (response) {
							$("#saveMsg").show().html('<div class="alert alert-danger"<strong>' + response.data.errorMessage + '</strong> </div>').fadeOut(5000);
						});
					form.$setPristine(true);
				}
			}
		};


		$scope.clear = function () {
			$("#pic").replaceWith($("#pic").val('').clone(true));
			$scope.tenant = {};
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
			var tenantId = $cookies.get('tenantId');
			var defer = $q.defer();

			$http.get($rootScope.ctx + '/getAllTenants/' + tenantId).then(function (result) {
				defer.resolve(result.data);
			});
			return defer.promise;
		}).withPaginationType('full_numbers').withOption('createdRow', createdRow);
		function createdRow(row, data, dataIndex) {
			$compile(angular.element(row).contents())($scope);
		}

		vm.dtColumns = [
			DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
			DTColumnBuilder.newColumn('tenantCode').withTitle('Vendor Code'),
			DTColumnBuilder.newColumn('tenantName').withTitle('Vendor Name'),
			DTColumnBuilder.newColumn('registredId').withTitle('Registred Id'),
			DTColumnBuilder.newColumn('countryMasterDto.countryName').withTitle('Country Name'),
			DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable().renderWith(actionsHtml)
		];

		function serialNoHtml() {
			return serial++;
		}

		function actionsHtml(data, type, full, meta) {
			vm.singleRecord[full.tenantId] = full;
			return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="vendorCtrl.edittenantdata(vendorCtrl.singleRecord[' + full.tenantId + '])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="vendorCtrl.deletetenantbyid(vendorCtrl.singleRecord[' + full.tenantId + '])"></button></span>';
		}

		vm.edittenantdata = function (singleObj) {
			$scope.tenant = singleObj;
			$scope.tenantPrivilagesList = [];
			for (var i = 0; i < tenantpre.length; i++) {
				$scope[tenantpre[i]] = false;
			}
			if (singleObj.tenantPrivileges) {
				$scope.tenantPrivilagesList = singleObj.tenantPrivileges.split(",");
				for (var j = 0; j < $scope.tenantPrivilagesList.length; j++) {
					$scope[$scope.tenantPrivilagesList[j].replace("-", "")] = true;
				}
			}
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
						url: $rootScope.ctx + '/deleteTenant/' + singleObj.tenantId
					}).then(function onSuccess(response) {
						vm.reloadtenantData();
						$("#saveMsg").show().html('<div class="alert alert-success"<strong>' + 'deleted successfully' + '</strong> </div>').fadeOut(3000);
					},
						function (err) {
							$("#saveMsg").show().html('<div class="alert alert-success"<strong>Failed</strong> </div>').fadeOut(3000);
						})
				}
			});
		}

		vm.reloadtenantData = function () {
			var tenantId = $cookies.get('tenantId');
			$scope.dtInstance.reloadData(function () {
				var defer = $q.defer();
				$http.get($rootScope.ctx + '/getAllTenants/' + tenantId).then(
					function (result) {
						defer.resolve(result.data);
					});
				return defer.promise;
			}, true);
		}

		$scope.getAllStates();
		$scope.getAllCountries();


	}]);