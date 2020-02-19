app.controller('forgotCtrl', ["$scope","$rootScope","$http", "$state", "SweetAlert", function ($scope,$rootScope,$http, $state, SweetAlert) {

    $scope.data={};
	$scope.forgotPassword = function(){
		if($scope.data.userLoginId && $scope.data.emailId) {
		        $http({
	            method: 'POST',
	            url: $rootScope.ctx +'/forgotPassword',
	            params:{userLoginId:$scope.data.userLoginId,emailId:$scope.data.emailId},
	            headers: {'Content-Type': 'application/json'}
	            }).then(function(response) {
	            	$rootScope.userLoginId=response.data.userLoginId;
	            	$state.go('login.reset');
		        }).catch(function(response){
		        	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
		        });
		}
	}
}])