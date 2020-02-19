'use strict';
/** 
  * controller for User Profile Example
*/
app.controller('UserCtrl', ['$scope','$q','$cookies','UserService','flowFactory','$rootScope','$http', function ($scope,$q,$cookies,UserService,flowFactory,$rootScope,$http) {

	  $scope.removeImage = function () {
	        $scope.noImage = true;
	        $scope.userInfo['pic']="";
	    };
    $scope.obj = new Flow();

    $scope.userInfo = {};
    
  
  
    
    
    
    $scope.loginTime = $cookies.get('loginTime');
    $scope.logOutTime=$cookies.get('logOutTime');
    $scope.ipAddress=$cookies.get('ipAddress');
    
    
    if($scope.loginTime!="null"){
    	 $scope.i=true;
    }
    else{
    	 $scope.i=false;
    }
    
    if($scope.logOutTime!="null"){
   	 $scope.j=true;
   }
   else{
   	 $scope.j=false;
   }
    if($scope.ipAddress!="null"){
      	 $scope.k=true;
      }
      else{
      	 $scope.k=false;
      }
    
    
    
    
    $scope.getUserInfo= function ()
	{
		$http({
	        method: 'GET',
	        url: $rootScope.ctx +'/getUserInfo',
	        params: {"userId": $rootScope.userId},
	       
	        }).then(function (response) {
	        	$scope.userInfo=response.data;
	        	console.log($scope.userInfo)
	        	if(!$scope.userInfo.pic){
	        		  $scope.noImage = true;
	        	}
		    }).catch(function onError(response) {
		    	
		    });
	}
    
    
    
    
	 $scope.form = {
		        submit: function (form) {
		        	console.log(form)
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
		            	
		            	$scope.loginDetails={};
		            	$scope.loginDetails.userId=$cookies.get('userId');
		        		$scope.loginDetails.tenantId=$cookies.get('tenantId');
		        		$scope.loginDetails.tenantType=$cookies.get('tenantType');
		        		$scope.userInfo.password=$cookies.get('password');
		        		UserService.saveUser($scope.userInfo,$scope.loginDetails).then(function(response){
		                   $("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
		        		})
		               .catch(function(response){
		                    $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
		               });
		    			form.$setPristine(true); 
			       }
		        }
		    };
		
	 $scope.clear=function(){
			$scope.userInfo={};	 
		}
	  
	    $scope.processFiles = function(files){
	        angular.forEach(files, function(flowFile, i){
	           var fileReader = new FileReader();
	              fileReader.onload = function (event) {
	            	  $scope.userInfo['pic']= event.target.result.split(",")[1];
	            
	              };
	              fileReader.readAsDataURL(flowFile.file);
	        });
	      };
	 // get countries
    function getCountry() {
		$http({
			method : 'get',
			url : $rootScope.ctx +'/getAllCountries',
			async : false,
		}).then(function(response) {
			$scope.countries = response.data;
		}, function(error) {
			console.log(error, 'can not get countries.');
		});
	}
  //get states
	function getState() {
		$http({
			method : 'get',
			url :  $rootScope.ctx +'/getAllStates',
			async : false,
		}).then(function(response) {
			$scope.states = response.data;
		}, function(error) {
			console.log(error, 'can not get states.');
		});
	}
    $scope.getUserInfo();
    getCountry();
    getState();
    
}]);