app.controller('resetCtrl', ["$scope","$http","$rootScope", "$state", "SweetAlert", "$timeout", function ($scope,$http,$rootScope, $state, SweetAlert, $timeout) {
	$scope.counter = 300;
	timer();
	$scope.resetPassword = function(){
		if($scope.otp && $scope.newPassword && $scope.confirmPassword) {
			$http({
	            method: 'POST',
	            url: $rootScope.ctx +'/resetPassword',
	            params:{userLoginId:$rootScope.userLoginId,otp:$scope.otp,newPassword:$scope.newPassword,confirmPassword:$scope.confirmPassword},
	            headers: {'Content-Type': 'application/json'}
	            }).then(function(response) {
	            	$scope.logout();
		        }).catch(function(response){
		        	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
		        });
			
		}
	}
	
	$scope.resendOTP = function() {
		$http({
            method: 'POST',
            url: $rootScope.ctx +'/resendOTP',
            params:{userLoginId:$rootScope.userLoginId},
            headers: {'Content-Type': 'application/json'}
            }).then(function(response) {
            	$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data+'</strong> </div>');
            	$scope.counter = 300;
	        }).catch(function(response){
	        	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
	        });
	}
	
	function timer() {
	    $scope.onTimeout = function(){
	        if ($scope.counter > 0){ $scope.counter--;
	        	$timeout($scope.onTimeout,1000);
	        }
	    }
	   $timeout($scope.onTimeout,1000);
	}
}])
app.filter('secondsToDateTime', [function() {
	    return function(seconds) {
	        return new Date(1970, 0, 1).setSeconds(seconds);
	    };
}])