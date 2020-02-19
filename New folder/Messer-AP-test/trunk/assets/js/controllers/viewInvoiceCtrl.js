'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('viewInvoiceCtrl', ["$scope", "$rootScope", "$http", "$stateParams", "ViewInvoiceService","SendToErpService","SendToCustomer","refreshToErpService", "$timeout","$cookies", function ($scope,$rootScope, $http, $stateParams, ViewInvoiceService,SendToErpService,SendToCustomer, refreshToErpService,$timeout,$cookies ) {
	$scope.loginDetails={};
	$scope.data={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.companydetails={};
	$scope.map={};
	$scope.map[0]="Drafted";
	$scope.map[1]="Submitted";
	$scope.map[2]="Saved";
	$scope.map[3]="Matched";
	$scope.map[4]="NotMatched";
	$scope.map[5]="Approved";
	$scope.map[6]="PaymentReceived";
	$scope.map[7]="Sucess";
	$scope.map[8]="ApprovedStatus";
	$scope.map[9]="Transactions_Entered";
	ViewInvoiceService.viewInvoice($scope.data,$scope.loginDetails).then(function (response) {
	        	$scope.data = response.data;
	        	$scope.findByuserId($scope.data.userId)
	        	getWorkFlowStatus($scope.data.workFlowStatus);
	        	if($scope.data.workFlowStatus==4 && response.data.errorDesc){
	        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorDesc+'</strong> </div>');
	        	}
	        	if($scope.data.workFlowStatus==3){
	        		$scope.isVisivble=true;
	        	}
	        	
	    },function (error){
	    	console.log(error, 'no data.');
		});
	
    $scope.findByuserId = function(userId) {
    	$http({
	        method: 'GET',
	        url: $rootScope.ctx +'/getUserInfo',
	        params: {"userId": userId},
	       
	        }).then(function (response) {
	        	$scope.userName=response.data.userLoginId;
	        	console.log($scope.userName);
		    }).catch(function onError(response) {
		    	
		    });
     } 
	 $scope.sendToErp = function(){	  
     
     SendToErpService.sendToErpObj($scope.data,$scope.loginDetails).then(function (response) {
     	if(response.data.Status=="MATCHED"){
     		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
     	}else{
     		
     		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.message+'</strong> </div>');
     	}
		    },function (error){
		    	console.log(error, 'no data.');
			});
     }
	 $scope.sendForApproval=function(){
	     SendToErpService.sendForApproval($scope.data,$scope.loginDetails).then(function (response) {
	    	 $("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
	 		    },function (error){
	 		    	console.log(error, 'no data.');
	 			});
	 }
	 
	 $scope.sendForReject=function(){
	     SendToErpService.sendForReject($scope.data.headerId,$scope.data.rejectReason,"PI").then(function (response) {
	    	 $("#saveMsg").show().html('<div class="alert alert-success"<strong><b>'+response.data+'</b></strong> </div>');
	 		    },function (error){
	 		    	console.log(error, 'no data.');
	 			});
	 }
	 
	 $scope.sendTocustomer=function(){
			SendToCustomer.sendToCustomerObj($scope.data,$scope.loginDetails).then(function (response) {
				$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
			},function (error){
				$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');

			});	
	}
	 
	   $scope.getTenant = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getTenant/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.companydetails=response.data;
				
		    },function (error){
		        console.log(error, 'can not get getBusinessPatner.');
		    });
        } 
	
	   $scope.getTenant();
	$scope.getAllCountries = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getAllCountries'
		    }).then(function (response) {
		        $scope.country = response.data;
		    },function (error){
		        console.log(error, 'can not get getAllCountries.');
		    });
         }
    $scope.getAllStates = function() {
	    $http({
	          method: 'GET',
	          url: $rootScope.ctx +'/getAllStates'
	    }).then(function (response) {
	        $scope.state = response.data;
	    },function (error){
	        console.log(error, 'can not get getDashboardCounts.');
	    });
     }
    
    $scope.refreshErp = function(){	        
        refreshToErpService.refreshToErpObj($scope.data).then(function (response) {
        	console.log(response.data);
    	    $scope.refreshErpData = response.data;
	    },function (error){
	    	console.log(error, 'no data.');
		});
    }
    function getWorkFlowStatus(workFlowStatus) {
    	if(workFlowStatus==0){
    		$("#invoiceStatus").show().html('<span class="alert blinkInfo">Drafted</span>');
    	}
    	if(workFlowStatus==1){
    		$("#invoiceStatus").show().html('<span class="alert blinkInfo">Submitted</span>');
    	}
    	if(workFlowStatus==2){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Saved</span>');
    	}
    	if(workFlowStatus==3){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Matched</span>');
    	}
    	if(workFlowStatus==4){
    		$("#invoiceStatus").show().html('<span class="alert blinkDanger">Not Matched</span>');
    	}
    	if(workFlowStatus==5){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Approved</span>');
    	}
    }
    
    
    $scope.logisVisible = false;
    
    $scope.logShowHide = function () {
        //If DIV is visible it will be hidden and vice versa.
        $scope.logisVisible = $scope.logisVisible ? false : true;
        $scope.showLog(); 
    }
    
    $scope.showLog = function(){
    	if($scope.data.headerId){ 
	 	$http({
	        method: 'GET',
	        url: $rootScope.ctx +'/showAuditLog/'+ $scope.data.headerId+'/'+'PI',
	        data: $scope.data,
	        headers: {'Content-Type': 'application/json' ,'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content') }
	        }).then(function (response) {
	        	console.log(response.data);

	        	angular.forEach(response.data, function(item) {
	        		
	        		var oldata = '';
	        		if(item.items[0]){
	        		for (var i = 0; i < item.items[0].length ; i++) { 
	        			var nldata = '';
	        			var nlparseData = JSON.parse(JSON.stringify(item.items[0][i]));
		        		
		        		for ( var key in nlparseData) {
							if (nldata) {
								nldata += ', ';
							}
							nldata += key + '=' + nlparseData[key];
						}
		        		item.items[0][i] = nldata;
	        		}
	        		}
	        		if(item.items[1]){
		        		for (var i = 0; i < item.items[1].length ; i++) { 
		        			var oldata = '';
		        			var olparseData = JSON.parse(JSON.stringify(item.items[1][i]));
			        		
			        		for ( var key in olparseData) {
								if (oldata) {
									oldata += ', ';
								}
								oldata += key + '=' + olparseData[key];
							}
			        		item.items[1][i] = oldata;
		        		}
		        		}
	        		var ndata = '';
	        		var odata = '';
					var nparseData = JSON.parse(item.newDate);
					for ( var key in nparseData) {
						if (ndata) {
							ndata += ', ';
						}
						ndata += key + '=' + nparseData[key];
					}
					item.newDate = ndata;
					var oparseData = JSON.parse(item.oldData);
					for ( var key in oparseData) {
						if (odata) {
							odata += ', ';
						}
						odata += key + '=' + oparseData[key];
					}
					item.oldData = odata;
				});
	        	$scope.log = response.data;
	        	console.log(response.data)
	        	$scope.log = response.data;
	        },function (error){
	        	console.log("error occured while saving. not saved");
	        });
	 	}
	}
    
    
    
    $scope.getAllStates();
    $scope.getAllCountries();

}]);
