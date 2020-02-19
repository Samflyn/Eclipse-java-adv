'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('viewCostInvoiceCtrl', ["$scope", "$rootScope", "$http","$filter", "$stateParams", "ViewCostInvoiceService","$timeout","$cookies","SendToErpService", function ($scope,$rootScope, $http,$filter, $stateParams,ViewCostInvoiceService,$timeout,$cookies,SendToErpService ) {
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.companydetails={};
	$scope.dimensionData=[{"id":"1",type:"Division"},{"id":"2",type:"Item Group"},{"id":"3",type:"Employee"},{"id":"4",type:"Country"},{"id":"5",type:"Business Partner"},{"id":"6",type:"Period"},{"id":"7",type:"Provision"},{"id":"8",type:"Van/Sales Order"}]
	$scope.editobj={};
	ViewCostInvoiceService.viewCostInvoice($scope.data,$scope.loginDetails).then(function (response) {
	        	$scope.data = response.data;
	        	for(var i=0;i<$scope.data.items.length;i++){
	        		if($scope.data.items[i].dimensionType){
	        			$scope.editobj = $filter('filter')($scope.dimensionData, {id:$scope.data.items[i].dimensionType},true)[0];
	        			$scope.data.items[i].dimensionType=$scope.editobj.type
	        		}
	        	}
	         	$scope.data.invoiceDate= moment(response.data.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	        	getWorkFlowStatus($scope.data.workFlowStatus);
	        	if($scope.data.workFlowStatus==4 && response.data.errorDesc){
	        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorDesc+'</strong> </div>');
	        	}
	        	
	    },function (error){
	    	console.log(error, 'no data.');
		});
	
	$scope.sendToErp = function(){
		for(var i=0;i<$scope.data.items.length;i++){
    		if($scope.data.items[i].dimensionsTypeMappingsdto){
    			$scope.editobj = $filter('filter')($scope.dimensionData, {type:$scope.data.items[i].dimensionType},true)[0];
    			$scope.data.items[i].dimensionType=$scope.editobj.id;
    		}
    	}
		console.log($scope.data);
        SendToErpService.sendCostToErpObj($scope.data,$scope.loginDetails).then(function (response) {
        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
		    },function (error){
		    	console.log(error, 'no data.');
			});
        }
	
	$scope.submitCostInvoiceForApproval = function(){
		$http({
            method: 'POST',
            url: $rootScope.ctx +'/submitCostInvoiceForApproval',
            params: {"headerId":$scope.data.headerId},
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
            }).then(function(response) {
            	console.log("(response.data",response.data);
            	if(response.data.workFlowStatus==5){
	        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>Cost Invoice Approved Sucessfully</strong> </div>');
	        	}else{
	        		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.message+'</strong> </div>');
	        	}
            	
            }).catch(function(response){
            	
            });
	}
	
	 $scope.sendForReject=function(){
	     SendToErpService.sendForReject($scope.data.headerId,$scope.data.rejectReason,"CI").then(function (response) {
	    	 $("#saveMsg").show().html('<div class="alert alert-success"<strong><b>'+response.data+'</b></strong> </div>');
	 		    },function (error){
	 		    	console.log(error, 'no data.');
	 			});
	 }
	
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
    
  
    //getting to fill ship To details
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
    function getWorkFlowStatus(workFlowStatus) {
    	if(workFlowStatus==0){
    		$("#invoiceStatus").show().html('<span class="alert blinkInfo"><strong>Drafted</strong></span>');
    	}
    	if(workFlowStatus==1){
    		$("#invoiceStatus").show().html('<span class="alert blinkInfo"><strong>Submitted</strong></span>');
    	}
    	if(workFlowStatus==2){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess"><strong>Saved</strong></span>');
    	}
    	if(workFlowStatus==3){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess"><strong>Matched</strong></span>');
    	}
    	if(workFlowStatus==4){
    		$("#invoiceStatus").show().html('<span class="alert blinkDanger"><strong>Not Matched</strong></span>');
    	}
    	if(workFlowStatus==5){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess"><strong>Approved</strong></span>');
    	}
    	if(workFlowStatus==9){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess"><strong>Transaction Entered</strong></span>');
    	}
    	if(workFlowStatus==6){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess"><strong>Payment</strong></span>');
    	}
    }
    
    
    
    if($stateParams.tabId){
    if($stateParams.tabId==0){
    	$scope.isenable=true
    }
    if($stateParams.tabId==3){
    	$scope.issubmitenable=true
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
	        url: $rootScope.ctx +'/showAuditLog/'+ $scope.data.headerId+'/'+'CI',
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


 

