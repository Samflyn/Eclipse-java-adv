'use strict';
/**
 * controllers used for the dashboard
 */
app.controller('changePasswordCtrl', [ "$scope","$http","$rootScope","$state", "$cookies", function($scope,$http,$rootScope,$state,$cookies) {
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
	                console.log("The form cannot be submitted because it contains validation errors!", "Errors are marked with a red, dashed border!");
	                return;
	            } else {
	            	
	            	if($scope.newPassword!=$scope.confirmPassword){
                    	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>New Password and Confirm Password Should be same</strong> </div>');
	            	}
	            	else{	
	            	$http({
	                    method: 'POST',
	                    url: $rootScope.ctx +'/changePassword',
	                    params:{userId:$cookies.get('userId'),newPassword:$scope.newPassword,confirmPassword:$scope.confirmPassword},
	                    headers: {'Content-Type': 'application/json'}
	                    }).then(function(response) {
	                    	$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data+'</strong> </div>');
	                    	$scope.logout();
	        	        }).catch(function(response){
	        	        	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
	        	        });
	    			form.$setPristine(true); 
	            }
		            }

	        }
	    };
	
	

} ]);
