'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('ProcessedDataQueueCtrl', ["$scope", "$rootScope","DTOptionsBuilder","DTColumnBuilder","$http","$q", "$compile", "$state","$cookies", "processedDataQueueService", function ($scope, $rootScope,DTOptionsBuilder, DTColumnBuilder, $http, $q, $compile, $state,$cookies, processedDataQueueService) {
    var vm = this;
    
    $scope.loginDetails={};
	$scope.loginDetails.userId =$cookies.get('userId');
    $scope.tenantType = $cookies.get('tenantType');
    
    $scope.workFlowStatus=7;
    
    $scope.params={};
    
    $scope.dtInstance={};
    var tableIndex = "0";	
    vm.reloadData = reloadData;
    
    // Data Table
    vm.dtOptions = DTOptionsBuilder
    .newOptions()
    .withDataProp('data')
    .withOption('processing', true)
    .withOption('serverSide', true)
    .withOption('ajax', {
	     url: $rootScope.ctx+'/getAllInvoices/'+data.tenantId+'/'+data.status+'/'+data.userId,
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
        	//console.log('res.responseJSON.data',res.responseJSON.data)
        }
	 })
    .withDisplayLength($scope.limit)
    .withPaginationType('full_numbers')
    .withOption('createdRow',createdRow)
    .withOption('lengthMenu', [[10, 25, 50, -1], [10, 25, 50, "All"]])
    
  
    vm.dtColumns = [  
        DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
        DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
        DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
        DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
        DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
        DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
        DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date').renderWith(changeDateFormat),  
        DTColumnBuilder.newColumn('referenceNo').withTitle('RefNo.'),  
        DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '50px')
    ];  
          
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
              return '<button type="button" class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>'+
                  '<button type="button"   class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +','+ full.workFlowStatus +')">'+'<i class="fa fa-trash"></i> </button>';
          }

            $scope.viewInvoice = function(headerId){
             
                $state.go('app.viewKpmg', {headerId: headerId},{tabId: 0});

            }
            
           var role= $cookies.get('roleName')
           
           
            $scope.loadInvoice = function(headerId){
            		 $state.go('app.edit', {headerId: headerId,});
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
            			        url:$rootScope.ctx +'/deleteInvoice/'+headerId
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
            
            
            function reloadData(status)
            {
            	
                console.log($scope.dtInstance)
                $scope.queryPath.status = status;
                $scope.dtInstance.reloadData(function() {
                  var defer = $q.defer();  
                  processedDataQueueService.processedDataQueue($scope.queryPath).then(function (result) {
                	  tableIndex = "0";
                        console.log("InvoiceQueue",result.data);  
                        defer.resolve(result.data);  
                    });  
                    return defer.promise; 
                }, true); 
            }
}]);