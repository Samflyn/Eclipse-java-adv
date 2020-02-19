'use strict';

app.controller('loginCntrl', ['$q', '$compile', '$scope', '$http', '$localStorage', '$rootScope', '$cookies', '$state', 'LoginService', function ($q, $compile, $scope, $http, $localStorage, $rootScope, $cookies, $state, LoginService) {
    $scope.loginData = {};
    //logout code
    $scope.login = function () {
        if ($scope.loginData.username && $scope.loginData.password) {
            $("#saveMsg1").html("");
            LoginService.login($scope.loginData).then(
                function onSuccess(response) {
                    if (response.status == '200') {
                        $localStorage.token = response.data.token;
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        let user = response.data.user;
                        $localStorage.user = user;
                        let role = user.roleMasterDto.roleName;
                        $cookies.put('roleName', role);
                        if (role == "SUPER_ADMIN") {
                            $state.go('app.superadminDashboard');
                        }else if (role == "ADMIN") {
                            $state.go('app.adminDashboard');
                        }else {
                            $state.go('app.dashboard');
                        }
                    } else {
                        $("#saveMsg1").show().html('<div class="alert alert-danger"<strong>' + response.data.errorMessage + '</strong> </div>').fadeOut(3000);
                    }
                }, function onError(response) {
                    $("#saveMsg1").show().html('<div class="alert alert-danger"<strong>' + response.data.errorMessage + '</strong> </div>').fadeOut(6000);
                });
        }
    }

    $scope.lockScreen = function () {
        if ($rootScope.userId) {
            if ($rootScope.password == $scope.loginData.password) {
                var tenantType = $cookies.get('tenantType');

                if (tenantType == "Vendor") {
                    $state.go('app.vandorDashboard');
                } else {
                    $state.go('app.dashboard');
                }
            } else {
                $("#saveMsg1").show().html('<div class="alert alert-danger"<strong>Invalid Password</strong> </div>').fadeOut(3000);
            }
        } else {
            $("#saveMsg1").show().html('<div class="alert alert-danger"<strong>Invalid session. Please login again.</strong> </div>').fadeOut(3000);
        }
    }
}]);