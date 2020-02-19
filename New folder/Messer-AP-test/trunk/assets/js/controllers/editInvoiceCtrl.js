'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('editInvoiceCtrl', ["$scope", "$rootScope", "$http", "$stateParams", "EditInvoiceService", "SendToErpService", "$timeout", "refreshToErpService","$cookies", function ($scope,$rootScope, $http, $stateParams, EditInvoiceService, SendToErpService, $timeout, refreshToErpService,$cookies ) {
	EditInvoiceService.editInvoice($scope.data).then(function (response) {
	        	$scope.data = response.data;
	        	getWorkFlowStatus($scope.data.workFlowStatus);
	    },function (error){
	    	console.log(error, 'no data.');
		});
	        $scope.sendToErp = function(){	        
	        SendToErpService.sendToErpObj($scope.data,$scope.loginDetails).then(function (response) {
	        	if(response.data.Status=="MATCHED"){
	        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
	        	}else{
	        		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
	        	}
                
	    },function (error){
	    	console.log(error, 'no data.');
            
		});

	        }

	   $scope.refreshErp = function(){	        
	        refreshToErpService.refreshToErpObj($scope.data).then(function (response) {
	        	$scope.refreshErpData = response.data;
	    },function (error){
	    	console.log(error, 'refreshErp');
		});
	  }

	    $scope.changeEvent = function(){
			   $timeout(function(){
				    $scope.isDirty = $scope.myForm.$dirty;
				  },100);
		   }
		$scope.samevalues = function(){
			$scope.changeEvent();
			if($scope.sameAsAbove){
				$scope.data.shipToName = $scope.data.bpName
				$scope.data.shipToRegisteredId = $scope.data.bpRegisteredId
				$scope.data.shipToState = $scope.data.bpStateId
				$scope.data.shipToAddress = $scope.data.bpAddress
			}else{
				$scope.data.shipToName = ''
				$scope.data.shipToRegisteredId = ''
				$scope.data.shipToState = ''
				$scope.data.shipToAddress = ''
			}
			
		}
		
		 $scope.sendToCustomer = function(){
	        	$scope.loginDetails={};
	        	$scope.loginDetails.userId=$cookies.get('userId');
	        	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	        	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	        	$http({
	                method: 'POST',
	                url: $rootScope.ctx +'/sendToCustomer',
	                data:$scope.data,
	                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
	                }).then(function(response) {
	                	$scope.data=response.data.invoiceData;
	                	getWorkFlowStatus($scope.data.workFlowStatus);
	                	$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
	                }).catch(function(response){
	                	$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.errorMessage+'</strong> </div>');
	                });
	     }

		 $scope.addLine = function() {					
			if($scope.data.bpStateId==null){
				 swal({ title:"", html:true, text:"Please Select Business Partner State"});
			}
			else{
				var linesdata = {
				'lineId' :'',
				'headerId':'',
				'docTypeId' : '',
				'descGoods': '',
				'inum': '',
				'qty': 1,
				'rate': '',
				'hsnSc': '',
				'txval': 0,
				'uqc': '',
				'rt': 0,
				'irt': 0,
				'iamt': 0,
				'crt': 0,
				'camt': 0,
				'srt': 0,
				'samt': 0,
				'csamt': 0,
				'csrt': 0,
				'ty': "G",                                     
				'lineValue': 0,
				'discountRate': 0,
				'discountAmount': 0,
				'discountrt':true,
				'discountamt':false,
		 
			}
				$scope.data.items.push(linesdata);
			}
	};
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
    		$("#invoiceStatus").show().html('<span class="alert blinkDanger">Matched</span>');
    	}
    	if(workFlowStatus==4){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Not Matched</span>');
    	}
    	if(workFlowStatus==5){
    		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Approved</span>');
    	}
    }

    $scope.getAllStates();
    $scope.getAllCountries();
}]);


 

