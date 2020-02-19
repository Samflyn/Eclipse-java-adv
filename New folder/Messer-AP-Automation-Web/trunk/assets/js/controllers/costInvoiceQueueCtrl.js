
app.controller('savedCostInvoiceQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$state","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$state,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
	$scope.data ={};
	 $scope.dtInstance={};
	$scope.loginDetails={};
   $scope.data.tenantDto;
   $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	var vm= this;
	 vm.singleRecord={};

	 var tableIndex = "0";	
	
	 $scope.workFlowStatus=2;

	 vm.dtOptions = DTOptionsBuilder
	    .newOptions()
	    .withDataProp('data')
	    .withOption('processing', true)
	    .withOption('serverSide', true)
	    .withOption('ajax', {
		     url: $rootScope.ctx+'/bulkupload/getAllInvoices/'+$scope.loginDetails.userId+"/"+$scope.workFlowStatus,
		     type: 'GET',
		     dataSrc: 'data',
		     data: function (d) {
	    	
		    	 $scope.params.start=0;
		    	 $scope.params.length=10;
		    	 $scope.params.value=d.search.value? d.search.value : '';
		           d.search=$scope.params;
		           tableIndex=d.start
		           return d;
		     },
	        reload: function () {
	        	tableIndex=0;
	            this.reload = true;
	            return this;
	        },
	        complete: function(res){
	        	tableIndex=0;
	        	$scope.tableData = res.responseJSON.data;
	        }
		 })
	    .withDisplayLength($scope.limit)
	    .withPaginationType('full_numbers')
	    .withOption('createdRow',createdRow)
	    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
		    
		    // Columns for Data Table
	        vm.dtColumns = [  
	            DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
	            DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
	            DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
	            DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
	            DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
	            DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	           
	            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date'),  
	           
	            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
	        ];  
     
		$scope.getManagerDetails=function(){
			$http({
				method : 'get',
        url :  $rootScope.ctx +'/getManagerDetails/'+$cookies.get('userId'),
        async : false,
			}).then(function(response) {
				$scope.isManager=response.data.isManager;
        	$scope.hasManager=response.data.hasManager;
        	console.log($scope.isManager)
			}, function(error) {
				console.log(error, 'can not get states.');
				});
			}  
		
		$scope.getManagerDetails();
	              function indexVal() {
	    				return ++tableIndex;
	    		  }
	    		  
	              function changeDateFormat(data, type, full, meta) {
	            	  return full.invoiceDate= moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	    				
	    		  }
	              $scope.deleteInvoice = function(headerId,workFlowStatus){
	              	console.log(workFlowStatus);
	              	   swal({
	                         title: "",
	                         text: "Are you sure want to delete",
	                         showCancelButton: true,
	                         confirmButtonColor: "#8B0000",
	                         confirmButtonText: "Yes",	
	                         closeOnConfirm: true
	                     }, function(isConfirm){
	                  	  if (isConfirm){
	                  		   $http({
	              			        method: 'DELETE',
	              			        url:$rootScope.ctx +'/bulkupload/deleteInvoice/'+headerId
	              					}).then(function onSuccess(response) {
	              					$("#saveMsg")
	              	        	   .show()
	              	        	   .html('<div class="alert alert-success"<strong>'+'deleted successfully'+'</strong> </div>')
	              	        	   .fadeOut(3000);
	              					$scope.dtInstance.reloadData();
	              					}, function(err) {
	              						
	              						$("#saveMsg")
	              			        	   .show()
	              			        	   .html('<div class="alert alert-success"<strong>Failed</strong> </div>')
	              			        	   .fadeOut(3000);
	              											})
	                  	  }
	                     });
	                

	              }
	              
	    		  function createdRow(row, data, dataIndex) {
	    		        $compile(angular.element(row).contents())($scope);
	    		  }
	              
	              function actionsHtml(data, type, full, meta) {
	                  return '<button type="button" class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>'+
	                  '<button type="button"  class="btn btn-primary margin-right-5" ng-click="loadInvoice('+ full.headerId +')">' +'<i class="fa fa-pencil-square-o"></i>' +'</button>'+
	                      '<button type="button"   class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +','+ full.workFlowStatus +')">'+'<i class="fa fa-trash"></i> </button>';
	              }

	                $scope.viewInvoice = function(headerId){
	                 
	                	$state.go('app.costInvoiceView', {'headerId':headerId,'tabId':0});
	                }
	    
	                $scope.loadInvoice = function(headerId){
	               		 $state.go('app.editCost', {headerId: headerId,});
	               }
		    //getting to fill ship To details
       $scope.getTenant = function() {
		    $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getTenant/'+$cookies.get('tenantId'),
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (response) {
		    	$scope.data.registeredName=response.data.tenantName;
		    	$scope.data.registeredId=response.data.registredId;
		    	$scope.data.registeredAddress=response.data.registredAddress;	
		          	console.log(response.data)
		    },function (error){
		        console.log(error, 'can not get company.');
		    });
        } 
      
     $scope.toCompleted = function(){$scope.$broadcast('completed-reload','');}
     $scope.transactions = function(){$scope.$broadcast('transaction-reload','');}
 	  $scope.toFailed = function(){$scope.$broadcast('failed-reload','');}
 	  $scope.toOpen = function(){$scope.dtInstance.reloadData();}
 	 $scope.reject = function(){$scope.$broadcast('rejected-reload','');}
 	
 	  
 	  
    $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
}]);

app.controller('approvedCostInvoiceQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$state","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$state,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
	$scope.data ={};
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	

	 $scope.workFlowStatus=5;

	 vm.dtOptions = DTOptionsBuilder
	    .newOptions()
	    .withDataProp('data')
	    .withOption('processing', true)
	    .withOption('serverSide', true)
	    .withOption('ajax', {
		     url: $rootScope.ctx+'/bulkupload/getAllInvoices/'+$scope.loginDetails.userId+"/"+$scope.workFlowStatus,
		     type: 'GET',
		     dataSrc: 'data',
		     data: function (d) {
	    	
		    	 $scope.params.start=0;
		    	 $scope.params.length=10;
		    	 $scope.params.value=d.search.value? d.search.value : '';
		           d.search=$scope.params;
		           tableIndex=d.start
		           return d;
		     },
	        reload: function () {
	        	tableIndex=0;
	            this.reload = true;
	            return this;
	        },
	        complete: function(res){
	        	tableIndex=0;
	        	$scope.tableData = res.responseJSON.data;
	        }
		 })
	    .withDisplayLength($scope.limit)
	    .withPaginationType('full_numbers')
	    .withOption('createdRow',createdRow)
	    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
		    
		    // Columns for Data Table
	        vm.dtColumns = [  
	            DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
	            DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
	            DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
	            DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
	            DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
	            DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	            DTColumnBuilder.newColumn('invoiceId').withTitle('DocumentNo'),
	            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(changeDateFormat),  
	           
	            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
	        ];  
	 $scope.getManagerDetails=function(){
         $http({
             method : 'get',
             url :  $rootScope.ctx +'/getManagerDetails/'+$cookies.get('userId'),
             async : false,
         }).then(function(response) {
             $scope.isManager=response.data.isManager;
             $scope.hasManager=response.data.hasManager;
             console.log("isManager ",$scope.isManager)
         }, function(error) {
             console.log(error, 'can not get states.');
         });
             }
	 $scope.getManagerDetails();
	              function indexVal() {
	    				return ++tableIndex;
	    		  }
	    		  
	              function changeDateFormat(data, type, full, meta) {
	            	  return full.invoiceDate= moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	    				
	    		  }
	              
	              
	    		  function createdRow(row, data, dataIndex) {
	    		        $compile(angular.element(row).contents())($scope);
	    		  }
	              
	              function actionsHtml(data, type, full, meta) {
	                  return '<button type="button" class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>';
	              }
	              
	    

	                $scope.viewInvoice = function(headerId){
	                 
	                    $state.go('app.costInvoiceView', {'headerId': headerId,'tabId':1});

	                }
	
	   $scope.$on('completed-reload',function(e,args){
		   $scope.dtInstance.reloadData();
		})
	 
 		
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
 
}]);

app.controller('paymentCostInvoiceQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$state","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$state,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
	$scope.data ={};
	
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	

	 $scope.workFlowStatus=6;

	 vm.dtOptions = DTOptionsBuilder
	    .newOptions()
	    .withDataProp('data')
	    .withOption('processing', true)
	    .withOption('serverSide', true)
	    .withOption('ajax', {
		     url: $rootScope.ctx+'/bulkupload/getAllInvoices/'+$scope.loginDetails.userId+"/"+$scope.workFlowStatus,
		     type: 'GET',
		     dataSrc: 'data',
		     data: function (d) {
	    	
		    	 $scope.params.start=0;
		    	 $scope.params.length=10;
		    	 $scope.params.value=d.search.value? d.search.value : '';
		           d.search=$scope.params;
		           tableIndex=d.start
		           return d;
		     },
	        reload: function () {
	        	tableIndex=0;
	            this.reload = true;
	            return this;
	        },
	        complete: function(res){
	        	tableIndex=0;
	        	$scope.tableData = res.responseJSON.data;
	        }
		 })
	    .withDisplayLength($scope.limit)
	    .withPaginationType('full_numbers')
	    .withOption('createdRow',createdRow)
	    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
		    
		    // Columns for Data Table
	        vm.dtColumns = [  
	            DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
	            DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
	            DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
	            DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
	            DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
	            DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(changeDateFormat),  
	           
	            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
	        ];  
	 $scope.getManagerDetails=function(){
         $http({
             method : 'get',
             url :  $rootScope.ctx +'/getManagerDetails/'+$cookies.get('userId'),
             async : false,
         }).then(function(response) {
             $scope.isManager=response.data.isManager;
             $scope.hasManager=response.data.hasManager;
             console.log("isManager ",$scope.isManager)
         }, function(error) {
             console.log(error, 'can not get states.');
         });
             }
	 $scope.getManagerDetails();
	              function indexVal() {
	    				return ++tableIndex;
	    		  }
	    		  
	              function changeDateFormat(data, type, full, meta) {
	            	  return full.invoiceDate= moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	    				
	    		  }
	              
	              
	    		  function createdRow(row, data, dataIndex) {
	    		        $compile(angular.element(row).contents())($scope);
	    		  }
	              
	              function actionsHtml(data, type, full, meta) {
	                  return '<button type="button" class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>';
	              }

	                $scope.viewInvoice = function(headerId){
	                 
	                    $state.go('app.costInvoiceView', {'headerId':headerId,'tabId':2});

	                }
	   $scope.$on('failed-reload',function(e,args){
	    	$scope.dtInstance.reloadData();
		})
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
 
}]);

app.controller('transactionCostInvoiceQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$state","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$state,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
	$scope.data ={};
	
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	

	 $scope.workFlowStatus=9;

	 vm.dtOptions = DTOptionsBuilder
	    .newOptions()
	    .withDataProp('data')
	    .withOption('processing', true)
	    .withOption('serverSide', true)
	    .withOption('ajax', {
		     url: $rootScope.ctx+'/bulkupload/getAllInvoices/'+$scope.loginDetails.userId+"/"+$scope.workFlowStatus,
		     type: 'GET',
		     dataSrc: 'data',
		     data: function (d) {
	    	
		    	 $scope.params.start=0;
		    	 $scope.params.length=10;
		    	 $scope.params.value=d.search.value? d.search.value : '';
		           d.search=$scope.params;
		           tableIndex=d.start
		           return d;
		     },
	        reload: function () {
	        	tableIndex=0;
	            this.reload = true;
	            return this;
	        },
	        complete: function(res){
	        	tableIndex=0;
	        	$scope.tableData = res.responseJSON.data;
	        }
		 })
	    .withDisplayLength($scope.limit)
	    .withPaginationType('full_numbers')
	    .withOption('createdRow',createdRow)
	    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
		    
		    // Columns for Data Table
	        vm.dtColumns = [  
	            DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
	            DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
	            DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
	            DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
	            DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
	            DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	            DTColumnBuilder.newColumn('invoiceId').withTitle('DocumentNo'),
	            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(changeDateFormat),  
	           
	            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
	        ];  
	 $scope.getManagerDetails=function(){
         $http({
             method : 'get',
             url :  $rootScope.ctx +'/getManagerDetails/'+$cookies.get('userId'),
             async : false,
         }).then(function(response) {
             $scope.isManager=response.data.isManager;
             $scope.hasManager=response.data.hasManager;
             console.log("isManager ",$scope.isManager)
         }, function(error) {
             console.log(error, 'can not get states.');
         });
             }
     $scope.getManagerDetails();
	              function indexVal() {
	    				return ++tableIndex;
	    		  }
	    		  
	              function changeDateFormat(data, type, full, meta) {
	            	  return full.invoiceDate= moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	    				
	    		  }
	              
	              
	    		  function createdRow(row, data, dataIndex) {
	    		        $compile(angular.element(row).contents())($scope);
	    		  }
	              
	              function actionsHtml(data, type, full, meta) {
	            	  var invoiceAction='';
                	  invoiceAction+='<button type="button" class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>';

	                  if($scope.hasManager && $scope.loginDetails.userId!=full.approverId)
	                  {
	                	  invoiceAction+='<button type="button"   class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +','+ full.workFlowStatus +')">'+'<i class="fa fa-trash"></i> </button>';
	                  }

	                   if($scope.isManager && $scope.loginDetails.userId==full.approverId){
	                      invoiceAction+=' <button type="button" class="btn btn-success" ng-click="showCase.ApproveRecord('+ full.headerId +')">' +'<i class="fa fa-check"></i> </button>';
	                  }
	              
	              return invoiceAction;
	              }
	              vm.ApproveRecord=function(headerId){
	                  swal({
	                           title: "",
	                           text: "Are you sure you want to Approve",
	                           showCancelButton: true,
	                           confirmButtonColor: "#008000",
	                           confirmButtonText: "Yes",	
	                           closeOnConfirm: true
	                       }, function(isConfirm){
	                    	  if (isConfirm){
	                    		  $http({
	    			method : 'post',
	    			url :  $rootScope.ctx +'/submitCostInvoiceForApproval',
	                async : false,
	               headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)},
	                data:headerId,
	    		}).then(function(response) {
	                console.log(response.data)
	               	$scope.dtInstance.reloadData();
	              $("#saveMsg")
	                	  .show()
	                	   .html('<div class="alert alert-success"<strong>'+"Approved"+'</strong> </div>')
	                	    .fadeOut(3000);
	    		}, function(error) {
	    			console.log(error, 'can not get states.');
	    		});
	               }
	               });
	          
	           } 

	                $scope.viewInvoice = function(headerId){
	                	$state.go('app.costInvoiceView', {'headerId':headerId,'tabId':3});
	                }
	   $scope.$on('transaction-reload',function(e,args){
	    	$scope.dtInstance.reloadData();
		})
        $scope.deleteInvoice = function(headerId,workFlowStatus){
       	console.log(workFlowStatus);
       	   swal({
                  title: "",
                  text: "Are you sure want to delete",
                  showCancelButton: true,
                  confirmButtonColor: "#8B0000",
                  confirmButtonText: "Yes",	
                  closeOnConfirm: true
              }, function(isConfirm){
           	  if (isConfirm){
           		   $http({
       			        method: 'DELETE',
       			        url:$rootScope.ctx +'/bulkupload/deleteInvoice/'+headerId
       					}).then(function onSuccess(response) {
       					$("#saveMsg")
       	        	   .show()
       	        	   .html('<div class="alert alert-success"<strong>'+'deleted successfully'+'</strong> </div>')
       	        	   .fadeOut(3000);
       					$scope.dtInstance.reloadData();
       					}, function(err) {
       						
       						$("#saveMsg")
       			        	   .show()
       			        	   .html('<div class="alert alert-success"<strong>Failed</strong> </div>')
       			        	   .fadeOut(3000);
       											})
           	  }
              });
         

       }
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
 
}]);

app.controller('rejectedQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$state","$compile","DTOptionsBuilder","DTColumnBuilder", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$state,$compile,DTOptionsBuilder, DTColumnBuilder,$timeout,$stateParams,$window) {
	$scope.data ={};
	
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	

	 $scope.workFlowStatus=10;

	 vm.dtOptions = DTOptionsBuilder
	    .newOptions()
	    .withDataProp('data')
	    .withOption('processing', true)
	    .withOption('serverSide', true)
	    .withOption('ajax', {
		     url: $rootScope.ctx+'/bulkupload/getAllInvoices/'+$scope.loginDetails.userId+"/"+$scope.workFlowStatus,
		     type: 'GET',
		     dataSrc: 'data',
		     data: function (d) {
	    	
		    	 $scope.params.start=0;
		    	 $scope.params.length=10;
		    	 $scope.params.value=d.search.value? d.search.value : '';
		           d.search=$scope.params;
		           tableIndex=d.start
		           return d;
		     },
	        reload: function () {
	        	tableIndex=0;
	            this.reload = true;
	            return this;
	        },
	        complete: function(res){
	        	tableIndex=0;
	        	$scope.tableData = res.responseJSON.data;
	        }
		 })
	    .withDisplayLength($scope.limit)
	    .withPaginationType('full_numbers')
	    .withOption('createdRow',createdRow)
	    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
		    
		    // Columns for Data Table
	        vm.dtColumns = [  
	            DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
	            DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
	            DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
	            DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
	            DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
	            DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
	            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(changeDateFormat),  
	           
	            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
	        ];  
	 $scope.getManagerDetails=function(){
         $http({
             method : 'get',
             url :  $rootScope.ctx +'/getManagerDetails/'+$cookies.get('userId'),
             async : false,
         }).then(function(response) {
             $scope.isManager=response.data.isManager;
             $scope.hasManager=response.data.hasManager;
             console.log("isManager ",$scope.isManager)
         }, function(error) {
             console.log(error, 'can not get states.');
         });
             }
	 $scope.getManagerDetails();
	              function indexVal() {
	    				return ++tableIndex;
	    		  }
	    		  
	              function changeDateFormat(data, type, full, meta) {
	            	  return full.invoiceDate= moment(full.invoiceDate, 'YYYY-MM-DD').format('DD/MM/YYYY');
	    				
	    		  }
	              
	              
	    		  function createdRow(row, data, dataIndex) {
	    		        $compile(angular.element(row).contents())($scope);
	    		  }
	              
	    	         function actionsHtml(data, type, full, meta) {
		            	  var invoiceAction='';
	                	  invoiceAction+= '<button type="button"  class="btn btn-primary margin-right-5" ng-click="loadInvoice('+ full.headerId +')">' +'<i class="fa fa-pencil-square-o"></i>' +'</button>';

		                  if($scope.hasManager && $scope.loginDetails.userId!=full.approverId)
		                  {
		                	  invoiceAction+='<button type="button"   class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +','+ full.workFlowStatus +')">'+'<i class="fa fa-trash"></i> </button>';
		                  }
		              return invoiceAction;
		              }

	                $scope.loadInvoice = function(headerId){
	               		 $state.go('app.editCost', {headerId: headerId});
	               }
	                $scope.deleteInvoice = function(headerId,workFlowStatus){
	                   	console.log(workFlowStatus);
	                   	   swal({
	                              title: "",
	                              text: "Are you sure want to delete",
	                              showCancelButton: true,
	                              confirmButtonColor: "#8B0000",
	                              confirmButtonText: "Yes",	
	                              closeOnConfirm: true
	                          }, function(isConfirm){
	                       	  if (isConfirm){
	                       		   $http({
	                   			        method: 'DELETE',
	                   			        url:$rootScope.ctx +'/bulkupload/deleteInvoice/'+headerId
	                   					}).then(function onSuccess(response) {
	                   					$("#saveMsg")
	                   	        	   .show()
	                   	        	   .html('<div class="alert alert-success"<strong>'+'deleted successfully'+'</strong> </div>')
	                   	        	   .fadeOut(3000);
	                   					$scope.dtInstance.reloadData();
	                   					}, function(err) {
	                   						
	                   						$("#saveMsg")
	                   			        	   .show()
	                   			        	   .html('<div class="alert alert-success"<strong>Failed</strong> </div>')
	                   			        	   .fadeOut(3000);
	                   											})
	                       	  }
	                          });
	                     

	                   }
	   $scope.$on('rejected-reload',function(e,args){
	    	$scope.dtInstance.reloadData();
		})
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
 
}]);
