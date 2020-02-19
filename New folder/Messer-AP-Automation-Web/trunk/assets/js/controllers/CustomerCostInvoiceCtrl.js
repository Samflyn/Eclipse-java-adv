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
							itemName : params
						});
						angular.forEach(result, function(item) {
							item['value'] = item['itemName'];
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


app.controller('CustomerCostInvoiceCtrl', ["$scope","$rootScope", "$http","$cookies","$filter","GenerateInvoiceService", "$timeout", "SendToErpService", "refreshToErpService","$state","$stateParams","$sce","$window",function ($scope,$rootScope, $http,$cookies,$filter,GenerateInvoiceService, $timeout, SendToErpService, refreshToErpService,$state,$stateParams,$sce,$window) {
	
	$scope.data ={};
	$scope.data.financialPeriod=$filter('date')(new Date(),'MMyyyy');
	$scope.data.items = [];
	$scope.data.invoiceType='Cost Invoice';
	$scope.loginDetails={};
	$scope.data.content=false;
	$scope.companydetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.loginDetails.userLoginId=$cookies.get('userLoginId');
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
	        	                return;
	        	            } else {
	        	            	$scope.data.pdffile= $scope.pdffile1;
	        	            
	        	            	 if($scope.data.items.length==0){
		        		           $("#saveMsg").show().html('<div class="alert alert-danger"<strong>Invoice contains at least one item</strong> </div>');
	        	            	 }
	        	            	 else{
	        	            		 console.log($scope.data);
	        	            		 $("#saveMsg").show().html('');
	        	            		 if($scope.data.bpName){
	     		    		    		$scope.data.errorDesc="";
	     		    		    	}
		        	            	GenerateInvoiceService.generateCostInvoice($scope.data,$scope.loginDetails).then(function(response){
		        		               $("#saveMsg").show().html('<div class="alert alert-success"<strong>CostInvoice Saved Sucessfully</strong> </div>');
		        		               $scope.data=response.data;
		        		               
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
			if($scope.data.bpRegisteredId==null){
				 swal({ title:"", html:true, text:"Please Enter Business Partner Registered ID"});
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
				'freight':0,
				'packing':0,
				'insurance':0,
				'currency':$scope.companydetails.currency,
				
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
		
	
	$scope.updateAmount=function(){
		
		var totalsum=0;
		angular.forEach($scope.data.items,function(key,value){
			console.log("key"+key);
			console.log("value"+value);
			$scope.data.items[value].txval=$scope.data.items[value].qty*$scope.data.items[value].productRate;
			var lineval=($scope.data.items[value].txval*$scope.data.items[value].rate)/100;
			
			var finallinevalue=$scope.data.items[value].txval+lineval;
			
			$scope.data.items[value].lineValue=parseFloat(Math.round(finallinevalue+ "e+"+2) + "e-"+2);
			
			totalsum=totalsum+$scope.data.items[value].lineValue;
		});
		
		$scope.data.invoiceValue=parseFloat(Math.round(totalsum+ "e+"+2)+ "e-"+2);
		
	}
	
	
	$scope.pdffile1="";
	
	$scope.calculateInvoivcevalue=function(){
		var ival=0;
		for(var i=0;i<$scope.data.items.length;i++){
			if($scope.data.items[i].lineValue){
				ival=parseFloat(ival)+parseFloat($scope.data.items[i].lineValue);
			}
		}
		$scope.data.invoiceValue=ival.toFixed(2);
	}
	 $scope.uploadImage=function(input) {
 		if (input.files && input.files[0]) {
 			var reader = new FileReader();
 			 var file = input.files[0];
 			 var img = $('<img/>', {
 	            id: 'dynamic',
 	            width:250,
 	            height:200
 	        });     
 			 reader.onload = function (e) {
 	            $(".image-preview-input-title").text("Change");
 	            $(".image-preview-clear").show();
 	            $(".image-preview-filename").val(file.name);            
 	            img.attr('src', e.target.result);
 	            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
 	           $scope.pdffile1 =e.target.result.split(",")[1] ;
 	          console.log($scope.data.pdffile);
 	        }     
 			reader.readAsDataURL(input.files[0]);
 			
 			console.log($scope.data.pdffile);
 		}

 	}
	 
	 $scope.getDimension= function(object,index){
		 $scope.rowindex=index;
		 $scope.dimensions=[];
			    $http({
			          method: 'GET',
			          url: $rootScope.ctx +'/getDimensions/'+ object,
			    }).then(function (response) {
			    	$scope.dimensions=response.data;
			    	console.log("reponse",$scope.dimensions);
			    	if($scope.dimensions.length>0) {
			    		angular.forEach($scope.dimensions,function(item,index){
			    		
			    			document.getElementById($scope.dimensions[index].dimensionType).setAttribute('required',true);
			    			$('#'+$scope.dimensions[index].dimensionType).addClass("highlightedTexts");
			    			
			    			
			    			
			    		});
			    		$('#myModel').modal('show');
 	            	 //  $("#saveMsg").show().html('<div class="alert alert-danger"<strong>For selected GL Account'+object.ledgerAccount+""+response.data.errorMessage+'</strong> </div>');
			    	}
			    	
			    },function (error){
			        console.log(error, 'can not get Dimensions.');
			    });
			 
			  
	 }
	 $scope.dimensionsList=[];
	
	 
	 $scope.saveDimension=function(){
		 if($scope.dimensionsList){
			 
			 $scope.dimensionsList=[];
		 }
		 console.log("dimensions from response are",$scope.dimensions);
		 $('#myModel').modal('hide');
		 
		 angular.forEach($scope.dimensions,function(item,index){
			 var dimensionData={
					
					 'dimensionType':0,
					 'dimensionValue':""
			 }
			 console.log(document.getElementById(item.dimensionType).value);
			 dimensionData.dimensionType=item.dimensionType;
			 dimensionData.dimensionValue=document.getElementById(item.dimensionType).value;
					 $scope.dimensionsList.push(dimensionData);
		 });
		 console.log($scope.dimensionsList);
		 console.log($scope.data.items);
		 $scope.data.items[$scope.rowindex].dimensionsTypeMappingsdto=$scope.dimensionsList;
		// linesdata['dimensionsTypeMappingsdto'] =  $scope.dimensionsList;
	 }
	 
	 
	 
	 
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
        		   $scope.updateAmount();
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
            		   $scope.updateAmount();
            		   });
            	   }
               });
		}
		}		
	$scope.bpObj={};         
	          $scope.uploadOCR=function() {
	        	 var file = $scope.myFile;
	        	 var formData = new FormData();
	        	 formData.append('file', file);
	        	 formData.append('invoiceType', "CI");
	     		 $http({
	     		        method: 'POST',
	     		        url: $rootScope.ctx +'/uploadOCR',
	     		        data: formData,
	     		        transformRequest: angular.identity,
	     		        headers: {'Content-Type': undefined,'registeredId':$rootScope.registeredId,'loginDetails':JSON.stringify($scope.loginDetails)}
	     		 	}).then(function onSuccess(response) {
	     		 			$scope.data=response.data;
/*	     		 			$scope.data.bpName=response.data.registeredName;
*/	     		 			var registeredid=response.data.registeredId;
	     		 			$scope.data.filepath=response.data.filePath;
	    					$scope.data.purchaseOrderNo=response.data.referenceNo;
	    					$scope.data.registeredName=$scope.companydetails.tenantName;
		    		    	$scope.data.registeredId=$scope.companydetails.registredId.trim();
		    		    	$scope.data.registeredAddress=$scope.companydetails.registredAddress;
	    					
		    		    	if( !$scope.data.bpName){
		    		    		$scope.data.errorDesc="Vendor Name not found"
		    		    		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+$scope.data.errorDesc+'</strong> </div>');	
		    		    	}
	    				
		     		 		/*	
		    		    	$scope.data.ShipToState=$scope.companydetails.stateMasterDto.stateId;
		    		    	$scope.data.ShipToCountry=$scope.companydetails.countryMasterDto.countryId;*/
		    		    	//$scope.bpObj = $filter('filter')($scope.businesspatnersList, {bpRegisteredId: registeredid})[0]; 
		    		   /* 	if($scope.bpObj){
		    		    		$scope.data.bpName=$scope.bpObj.bpName;
		    					$scope.data.bpAddress = $scope.bpObj.bpAddress;
		    					$scope.data.bpRegisteredId = $scope.bpObj.bpRegisteredId;
		    					$scope.data.bpStateId = $scope.bpObj.bpStateId;
		    					$scope.data.bpCountryId = $scope.bpObj.bpCountryId;
		    		    	}else{
		    		    		$scope.data.bpAddress = response.data.registeredAddress;
		    					$scope.data.bpRegisteredId = registeredid;
		    					$scope.data.bpStateId = response.data.stateId;
		    					$scope.data.bpCountryId = response.data.countryId;
		    		    	}
	    					console.log($scope.data.purchaseOrderNo)*/
	    					$rootScope.items1=$scope.data.items;
		     		 		angular.forEach($scope.data.items, function(value, key){
		     		 			$scope.data.items[key].qty=parseInt(value.qty);
		     		 			$scope.data.items[key].discountRate=parseInt(value.discountRate);
		     		 			$scope.data.items[key].uqc=value.uqc;
		     		 			$scope.data.items[key].currency=$scope.companydetails.currency;
		     		 			if(value.rate){
		     		 				$scope.data.items[key].rate=parseInt(value.rate);
		     		 			}else{
		     		 				$scope.data.items[key].rate=0;
		     		 			}
		     		 			/*var myString = value.descgoods;
		     		 			var dotPosition = myString.indexOf(".");
		     		 			var theBitBeforeTheDot = myString.substring(dotPosition+1,dotPosition.length);*/
		     		 			$scope.data.items[key].descGoods=value.descgoods;
		     		 			$scope.data.items[key].lineValue=Number(value.lineValue).toPrecision();

		     		 		});
	     		 		
	     			}, function(err) {
	     				$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+err.data.errorMessage+'</strong> </div>');
	     			});
	     	  }

	        $scope.sendToErp = function(){	  
	        SendToErpService.sendCostToErpObj($scope.data,$scope.loginDetails).then(function (response) {
	        	if(response.data.Status=="Transactions_Entered"){
	        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
	        	}else{
	        		
	        		$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.message+'</strong> </div>');
	        	}
	          }).catch(function(response){
		    	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
              });
	        }
	        
	        $scope.compareInvoices = function(data){	
	        	var fileName =$scope.data.filepath;
	        	if(fileName){
	        		$scope.filename1= fileName.split('\\').pop().split('/').pop();
	        		  GenerateInvoiceService.compareInvoices(fileName).then(function (result) {
	 	            	 var file =new Blob([result.data], {type: 'application/pdf'});
	 	                 var fileURL = URL.createObjectURL(file);
	 	                 console.log("pdf link", URL.createObjectURL(file));
	 	                 $scope.content=fileURL+"#toolbar=0";
	 	                 $('#ifmReport').attr('src', $scope.content);
	 	             });
	        	}
	        	else if($scope.dbfile) {
	        		$scope.content=$scope.dbfile+"#toolbar=0";
	        		$('#ifmReport').attr('src', $scope.content);
	        	}
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
        
        $scope.getAllLedgerAccounts = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getLedgerAccount',
		          headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	console.log(response.data)
		       $scope.ledgerAccount = response.data;
		    },function (error){
		        console.log(error, 'can not get getAllLedgerAccounts');
		    });
         }
        $scope.getAllLedgerAccounts();
        $scope.getBusinessPatner = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getParentTenant/'+$cookies.get('parentTenantId'),
		          headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	 var bpData= response.data;
		    	 console.log("bpData ",bpData);
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
        
        $scope.getVendors = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getAllTenants/'+$cookies.get('tenantId'),
		          headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.vendorData= response.data;
		    	 console.log("vendorData",$scope.vendorData);
		    },function (error){
		        console.log(error, 'can not get getVendors.');
		    });
		    }
        $scope.getVendors();
        $scope.populateFields=function(registeredid)
        {
        	angular.forEach($scope.vendorData, function(value, key) {
        		 if(value.registredId==registeredid){
        			 $scope.data.bpName = value.tenantName;
     		        $scope.data.bpAddress =value.registredAddress;
        		 }
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
        
        // getting to fill ship To details
        $scope.getTenant = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getTenant/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.companydetails=response.data;
				$scope.data.registeredName=$scope.companydetails.tenantName;
				 	
		    	$scope.data.registeredId=$scope.companydetails.registredId.trim();
		    	$scope.data.registeredAddress=$scope.companydetails.registredAddress;	
		    	$scope.data.ShipToState=$scope.companydetails.stateMasterDto.stateId;
		    	$scope.data.ShipToCountry=$scope.companydetails.countryMasterDto.countryId;
		    },function (error){
		        console.log(error, 'can not get getBusinessPatner.');
		    });
         } 
        // Auto filling data of business patner details
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
		          url: $rootScope.ctx +'/getUqcList/'+$scope.loginDetails.tenantId,
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.uqcList=response.data
		    	// console.log($scope.uqcList)
		 	    },function (error){
		        console.log(error, 'can not get uqc list.');
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
		$scope.autoFillItems = function(item) {
			if (item) {
				$scope.data.items[item.index].itemType = item.itemType
				$scope.data.items[item.index].uqc = item.uqc
				$scope.data.items[item.index].hsnSc = item.hsnCode
				$scope.data.items[item.index].rate = item.rate
			}
		}
        $scope.getAllCountries();
        $scope.getAllStates();
        $scope.getItems();
        $scope.getUqcList();
        $scope.getTenant();
        $scope.compareInvoices();
       
        $scope.getCustomerBusinessPatner();
      
        if($stateParams.headerId){
        	 
        	GenerateInvoiceService.editCostInvoice($scope.loginDetails).then(function (response) {
        		
        		
        	      $scope.data = response.data;
        	      if($scope.data.errorDesc){
        	    	  $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+$scope.data.errorDesc+'</strong> </div>');	  
        	      }
        	      console.log( $scope.data );  
        	      $scope.dbfile=response.data.pdffile;
        	      $scope.data.filepath=response.data.filepath;
		 			getWorkFlowStatus($scope.data.workFlowStatus);
		 			if($scope.data.workFlowStatus==4 && response.data.errorDesc){
		 			 $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorDesc+'</strong> </div>');
        	}
		 			
        	    },function (error){
        	    	console.log(error, 'no data.');
        		});
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
        
     

}]);


angular.module('filters-module', [])
.filter('trustAsResourceUrl', ['$sce', function($sce) {
    return function(val) {
        return $sce.trustAsResourceUrl(val);
    };
}]) 







