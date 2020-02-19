'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.directive('bpAutoComplete', [ '$filter', bpAutoCompleteDir]);
app.directive('itemsAutoComplete', [ '$filter','$http', itemsAutoCompleteDir]);
app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);

function bpAutoCompleteDir($filter) {
	return {
		restrict : 'A',
		link : function(scope, elem, attrs) {
			elem.autocomplete({
				source : function(request, response) {
					// term has the data typed by the user
					var params = request.term;
					// simulates api call with odata $filter
					var data = scope.businesspatnersList;
					if (data) {
						var result = $filter('filter')(data, {
							bpName : params
						});
						angular.forEach(result, function(item) {
							item['value'] = item['bpName'];
						});
					}
					response(result);
				},
				minLength : 1,
				select : function(event, ui) {
					// force a digest cycle to update the views
					scope.$apply(function() {
						scope.setCustomerData(ui.item);
					});
				},
			});
		}
	};
}

function itemsAutoCompleteDir($filter,$http) {
	return {
		restrict : 'A',
		link : function(scope, elem, attrs) {
			elem.autocomplete({
				source : function(request, response) {
					var data1 = attrs.index 
					// term has the data typed by the user
					var params = request.term;
					// simulates api call with odata $filter
					var data = scope.itemsList;
					if (data) {
						var result = $filter('filter')(data, {
							itemDesc : params
						});
						angular.forEach(result, function(item) {
							item['value'] = item['itemDesc'];
							item['index'] = data1;
						});									
					}
					response(result);
				},
				minLength : 1,
				select : function(event, ui ) {
					// force a digest cycle to update the views
						scope.autoFillItems(ui.item);
						scope.$apply();
				}
			});
		}
	};
}


app.controller('generateInvoiceCtrl', ["$scope","$rootScope", "$http","$cookies","$filter","GenerateInvoiceService", "$timeout", "SendToErpService", "refreshToErpService", "$stateParams",function ($scope,$rootScope, $http,$cookies,$filter,GenerateInvoiceService, $timeout, SendToErpService, refreshToErpService,$stateParams) {
	$scope.data ={};
	$scope.data.financialPeriod=$filter('date')(new Date(),'MMyyyy');
	$scope.data.items = [];
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
				if($scope.data.workFlowStatus==0){
					$("#invoiceStatus").show().html('<span class="alert blinkInfo">Drafted</span>');
				}
	        	if($scope.data.workFlowStatus==1){
	        		$("#invoiceStatus").show().html('<span class="alert blinkInfo">Submitted</span>');
	        	}
	        	if($scope.data.workFlowStatus==2){
	        		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Saved</span>');
	        	}
	        	if($scope.data.workFlowStatus==3){
	        		$("#invoiceStatus").show().html('<span class="alert blinkDanger">Matched</span>');
	        	}
	        	if($scope.data.workFlowStatus==4){
	        		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Not Matched</span>');
	        	}
	        	if($scope.data.workFlowStatus==5){
	        		$("#invoiceStatus").show().html('<span class="alert blinkSuccess">Approved</span>');
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
	        	                console.log("The form cannot be submitted because it contains validation errors!", "Errors are marked with a red, dashed border!");
	        	                return;
	        	            } else {
	        	            	 if($scope.data.items.length==0){
		        		           $("#saveMsg").show().html('<div class="alert alert-danger"<strong>Invoice contains at least one item</strong> </div>');
	        	            		
	        	            	 }
	        	            	 else{
		        	            	GenerateInvoiceService.generateInvoice($scope.data,$scope.loginDetails).then(function(response){
		        		               $("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
		        		               $scope.data.headerId=response.data.headerId;
		        	                })
	        	               .catch(function(response){
	        	            	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
	        	               });
	        	    			form.$setPristine(true); 
	        		            }
	        	            }
	        	        }
	        	    }; 	

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
					
	$scope.deleteLine=function(index){
		
		if($scope.data.items[index].lineId!=''){
			var textmsg;
    	if($scope.data.items.length>1){
    		textmsg = "Are you sure want to delete the Item ?(Please save the Invoice After deleting the Item)"
    	}
    	if($scope.data.items.length==1){
    		textmsg = "Are you sure want to delete the Item? (Atleast one Item should be there to save the Invoice)"
    	}
    	
    	swal({
               title: "",
               text: textmsg,
               showCancelButton: true,
               confirmButtonColor: "#8B0000",
               confirmButtonText: "Yes",
               closeOnConfirm: true
           }, function(isConfirm){
        	   if (isConfirm){ 
        		   $scope.$apply(function(){
        		   $scope.data.items.splice( index, 1 );
        		   });
        	   }
           });
		}else{
			swal({
                   title: "",
                   text: "Are you sure want to delete the Item?",
                   showCancelButton: true,
                   confirmButtonColor: "#8B0000",
                   confirmButtonText: "Yes",
                   closeOnConfirm: true
               }, function(isConfirm){
            	   if (isConfirm){ 
            		   $scope.$apply(function(){
            		   $scope.data.items.splice( index, 1 );
            		   });
            	   }
               });
			
			
		}
		}		
			          
	          $scope.uploadOCR=function() {
	        	 var file = $scope.myFile;
	        	 var formData = new FormData();
	        	 formData.append('file', file);
	        	 formData.append('invoiceType', "PI");
	     		 $http({
	     		        method: 'POST',
	     		        url: $rootScope.ctx +'/uploadOCR',
	     		        data: formData,
	     		        transformRequest: angular.identity,
	     		        headers: {'Content-Type': undefined,'registeredId':$rootScope.registeredId,'tenantId':1}
	     		 	}).then(function onSuccess(response) {
	     		 		console.log("response {}",response.data);
	     		 		$scope.data=response.data;
	     		 		angular.forEach($scope.data.items, function(value, key){
	     		 			$scope.data.items[key].qty=parseInt(value.qty);
	     		 			$scope.data.items[key].discountRate=parseInt(value.discountRate);
	     		 			$scope.data.items[key].uqc=value.uqc;
	     		 		});
	     			}, function(err) {
	     				console.log("err {}",err);
	     			});
	     	  }

	        $scope.sendToErp = function(){	  
	        
	        SendToErpService.sendToErpObj($scope.data).then(function (response) {
	        	if(response.data.Status=="Matched"){
	        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
	        	}else{
	        		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.message+'</strong> </div>');
	        	}
                
			    },function (error){
			    	console.log(error, 'no data.');
				});
	        }
	        $scope.refreshErp = function(){	        
	        refreshToErpService.refreshToErpObj($scope.data).then(function (response) {
	        	$scope.refreshErpData = response.data;
			    },function (error){
			    	console.log(error, 'no data.');
		            
				});

	        }
	  
		$scope.samevalues = function(){
			if($scope.sameAsAbove){
				$scope.data.shipToName = $scope.data.bpName
				$scope.data.shipToRegisteredId = $scope.data.bpRegisteredId
				$scope.data.shipToState = $scope.data.bpStateId
				$scope.data.shipToCountryId = $scope.data.bpCountryId
				$scope.data.shipToAddress = $scope.data.bpAddress
			}else{
				$scope.data.shipToName = ''
				$scope.data.shipToRegisteredId = ''
				$scope.data.shipToState = ''
				$scope.data.shipToCountryId = ''
				$scope.data.shipToAddress = ''
			}
			
		}

	    $scope.clear = function(){
			   $scope.data ={};
			   $scope.data.bpStateId = "";
			   $scope.data.shipToState = "";
			   $scope.myForm.$submitted=false;
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
        
        $scope.getBusinessPatner = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getParentTenant/'+$cookies.get('parentTenantId'),
		          headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	var bpData= response.data;
		    	if(bpData){
		    		$scope.data.bpName = bpData.bpName;
			        $scope.data.bpRegisteredId = bpData.bpRegisteredId;
			        $scope.data.bpCountryId = bpData.bpCountryId;
			        $scope.data.bpStateId = bpData.bpStateId;
			        $scope.data.bpAddress = bpData.bpAddress;
		    	}
		    },function (error){
		        console.log(error, 'can not get getBusinessPatner.');
		    });
         }
        
        $scope.getCustomerBusinessPatner = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getBusinessPatners/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.businesspatnersList=response.data;
		    },function (error){
		        console.log(error, 'can not get getBusinessPatner.');
		    });
         } 
        
        //Auto filling data of business patner details
		$scope.setCustomerData = function(item) {
			if (item) {
				$scope.selectedUser = item;
				$scope.data.bpAddress = item.bpAddress;
				$scope.data.bpRegisteredId = item.bpRegisteredId;
				$scope.data.bpStateId = item.bpStateId;
				$scope.data.bpCountryId = item.bpCountryId;
			}
		}
		
		$scope.getItems = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getItems/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.itemsList=response.data
		 	    },function (error){
		        console.log(error, 'can not getItems.');
		    });
         }
		
		$scope.getUqcList = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getUqcList',
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.uqcList=response.data
		    	console.log($scope.uqcList)
		 	    },function (error){
		        console.log(error, 'can not get uqc list.');
		    });
         }   
	
	
		$scope.autoFillItems = function(item) {
			if (item) {
				$scope.data.items[item.index].itemType = item.itemType
				$scope.data.items[item.index].uqc = item.uqc
				$scope.data.items[item.index].hsnCode = item.hsnCode
				$scope.data.items[item.index].productRate = item.rate
			}
		}

        $scope.getAllCountries();
        $scope.getAllStates();
        $scope.getItems();
        $scope.getUqcList();
        if($cookies.get('tenantType')!='VENDOR'){
        	$scope.getCustomerBusinessPatner();
        }else{
        	$scope.getBusinessPatner();
        }
      
      
        if($stateParams.headerId){
        	 
        	GenerateInvoiceService.editInvoice().then(function (response) {
        	      $scope.data = response.data;
        	    },function (error){
        	    	console.log(error, 'no data.');
        		});
        }
    

}]);
