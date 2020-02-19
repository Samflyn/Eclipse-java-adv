'use strict';
app.controller('GeneratedQueueCtrl', ["$scope", "$rootScope","DTOptionsBuilder","DTColumnBuilder","$http","$q", "$compile", "$state","$cookies", "InvoiceQueueService", function ($scope, $rootScope,DTOptionsBuilder, DTColumnBuilder, $http, $q, $compile, $state,$cookies, InvoiceQueueService) {
        var vm = this;
        $scope.queryPath= {financialPeriod: $rootScope.financialPeriod, tenantId: $cookies.get('tenantId'),userId: $cookies.get('userId')}
        $scope.tenantType = $cookies.get('tenantType');
        $scope.dtInstance={};
        $scope.queryPath.status=$scope.tenantType=='VENDOR' ? 0 : 2;
    	$scope.loginDetails={};
		$scope.loginDetails.userId=$cookies.get('userId');
		$scope.loginDetails.tenantId=$cookies.get('tenantId');
		$scope.loginDetails.tenantType=$cookies.get('tenantType');
        var tableIndex = "0";	
    	vm.singleRecord={};
    	   $scope.params={};
        vm.reloadData = reloadData;
        
     // Data Table
        vm.dtOptions = DTOptionsBuilder
        .newOptions()
        .withDataProp('data')
        .withOption('processing', true)
        .withOption('serverSide', true)
        .withOption('ajax', {
    	     url: $rootScope.ctx+'/getAllInvoices/'+$cookies.get('tenantId')+'/'+$cookies.get('userId'),
    	     type: 'GET',
    	     dataSrc: 'data',
    	     data: function (d) {
    	    	 d.status= $scope.queryPath.status;
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
            DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date'),  
            DTColumnBuilder.newColumn('purchaseOrderNo').withTitle('PO No.'),  
            DTColumnBuilder.newColumn('workFlowStatus').withTitle('Status').renderWith(status),  
            DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml).withOption('width', '200px')
        ];  
              
              function indexVal() {
    				return ++tableIndex;
    		  }
        
              function createdRow(row, data, dataIndex) {
  		        $compile(angular.element(row).contents())($scope);
  		       }
        
      

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
            function status(data, type, full, meta) {
                    if(full.workFlowStatus==0){
                        return "Drafted";
                    }
                    if(full.workFlowStatus==1){
                        return $scope.tenantType=='CUSTOMER' ? 'Received' : 'Submitted';
                    }
                    if(full.workFlowStatus==2 ){
                        return full.approvedStatus==8 ? 'Pending' : 'Saved';
                    }
                    if(full.workFlowStatus==3){
                        return "Matched";
                    }
                    if(full.workFlowStatus==4){
                        return "Not Matched";
                    }
                    if(full.workFlowStatus==5){
                        return "Approved";
                    }
                    if(full.workFlowStatus==6){
                        return "Payment Received";
                    }
                    if(full.workFlowStatus==10){
                        return "Rejected";
                    }
                
                }
    //To get the serial No in Data Table 
          function indexVal() {
                return ++tableIndex; 
            }
          function actionsHtml(data, type, full, meta) {
           var invoiceAction='';
              if(full.workFlowStatus!=2 || full.workFlowStatus!=0){
                  invoiceAction+='<button type="button"  class="btn btn-primary margin-right-5" ng-click="viewInvoice('+ full.headerId +')">' +'<i class="fa fa-eye"></i>' +'</button>';
              }
          	if($scope.tenantType=='CUSTOMER' && full.workFlowStatus!=3){

                invoiceAction+='<button type="button"  class="btn btn-primary margin-right-5" ng-click="loadInvoice('+ full.headerId +')">' +'<i class="fa fa-pencil-square-o"></i>' +'</button>';
                invoiceAction+='<button type="button"  class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +','+ full.workFlowStatus +')">'+'<i class="fa fa-trash"></i> </button>';
          	}
          	vm.singleRecord[full.headerId]=full;
              return invoiceAction;
            }

              $scope.viewInvoice = function(headerId){
                		$state.go('app.view', {headerId: headerId});
                }

    /*       var role= $cookies.get('roleName')
        vm.sendForapproval=function(singleObj){
        $http({
			method : 'post',
			url :  $rootScope.ctx +'/sendToManager',
            async : false,
           headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)},
            data:singleObj,
		}).then(function(response) {
            reloadData(singleObj.workFlowStatus)
          $("#saveMsg")
            	  .show()
            	   .html('<div class="alert alert-success"<strong>'+response.data+'</strong> </div>')
            	    .fadeOut(3000);
		}, function(error) {
			console.log(error, 'can not get states.');
		});
       }*/
    /*     vm.ApproveRecord=function(singleObj){
              swal({
                       title: "",
                       text: "Are you sure you want to Approve(Note:After approval this record will be send to ERP System )",
                       showCancelButton: true,
                       confirmButtonColor: "#008000",
                       confirmButtonText: "Yes",	
                       closeOnConfirm: true
                   }, function(isConfirm){
                	  if (isConfirm){
                		  $http({
			method : 'post',
			url :  $rootScope.ctx +'/sendToErpForApproval',
            async : false,
           headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify($scope.loginDetails)},
            data:singleObj,
		}).then(function(response) {
            console.log("response---------",response.data.message)
            reloadData(singleObj.workFlowStatus)
          $("#saveMsg")
            	  .show()
            	   .html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>')
            	    .fadeOut(6000);
		}, function(error) {
			console.log(error, 'can not get states.');
		});
           }
           });
      
       }*/
           
            $scope.loadInvoice = function(headerId){
            		 $state.go('app.edit', {headerId: headerId,});

            }
            $scope.deleteInvoice = function(headerId,workFlowStatus){
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
            					reloadData(workFlowStatus);
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
            	$scope.queryPath.status = status;
            	$scope.dtInstance.reloadData();
            }
 /*           $scope.reportDownload=function(){
            	$(".overlay").show();
    		    $http({
    		          method: 'GET',
    		          url: $rootScope.ctx +'/reportDownload',
    		          responseType: 'arraybuffer',
    		    }).then(function (response) {
    		    	var blob = new Blob([response.data]);
    				var objectUrl = URL.createObjectURL(blob);
    				var a = document.createElement("a");
    				a.style = "display:none";
    				a.href = objectUrl;
    				a.download = "report.xlsx";
    				a.click();  
    				$(".overlay").hide();
    		      
    		    }).catch(function(response){
    				var decodedString = String.fromCharCode.apply(null, new Uint8Array(response.data));
    				var obj = JSON.parse(decodedString);
    				var message = obj['errorMessage'];
    				$(".overlay").hide();

    			});
            }*/
            $scope.onload=function(){
            	$scope.getManagerDetails();
            }
            $scope.onload();
}]);

/*app.controller('savedQueueCtrl', ["$scope", "$rootScope","DTOptionsBuilder","DTColumnBuilder","$http","$q", "$compile", "$state", "savedQueueService", function ($scope, $rootScope,DTOptionsBuilder, DTColumnBuilder, $http, $q, $compile, $state, savedQueueService) {
    var vm = this; 
 
    var tableIndex = "0";
    vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
        var defer = $q.defer();  
        savedQueueService.savedQueue().then(function (result) {  
            defer.resolve(result.data);  
            console.log("InvoiceQueue",result.data);
        });  
        return defer.promise;  
    })
    .withPaginationType('full_numbers')
    .withOption('createdRow', function(row, data, dataIndex) {
        // Recompiling so we can bind Angular directive to the DT
        $compile(angular.element(row).contents())($scope);
    });  
  
    vm.dtColumns = [  
        DTColumnBuilder.newColumn(null).withTitle('S. No').notSortable().renderWith(indexVal),  
        DTColumnBuilder.newColumn('registeredId').withTitle('Registered ID'),  
        DTColumnBuilder.newColumn('registeredName').withTitle('Registered Name'),  
        DTColumnBuilder.newColumn('bpRegisteredId').withTitle('BP Registered ID'), 
        DTColumnBuilder.newColumn('bpName').withTitle('BP Registered Name'),  
        DTColumnBuilder.newColumn('invoiceNo').withTitle('Invoice Number'),  
        DTColumnBuilder.newColumn('invoiceDate').withTitle('Invoice Date'),  
        DTColumnBuilder.newColumn('purchaseOrderNo').withTitle('PO No.'),  
        DTColumnBuilder.newColumn('workFlowStatus').withTitle('Status').renderWith(status),  
        DTColumnBuilder.newColumn(null).withTitle('Action').notSortable().renderWith(actionsHtml)
    ];  

    //To get the serial No in Data Table 
          function status(data, type, full, meta) {
                if(full.workFlowStatus==1){
                    return "Registered";
                }
                if(full.workFlowStatus==2){
                    return "Matched";
                }
                if(full.workFlowStatus==3){
                    return "Not Matched";
                }
                if(full.workFlowStatus==4){
                    return "Approved";
                }
            }
          function indexVal() {
                return ++tableIndex;
            }
            function actionsHtml(data, type, full, meta) {
                return '<button type="button" class="btn btn-primary margin-right-5" ng-click="loadInvoice('+ full.headerId +')">' +
                    '<i class="fa fa-pencil-square-o"></i>' +
                    '</button>'+'<button type="button" class="btn btn-danger" ng-click="deleteInvoice('+ full.headerId +')">'+'<i class="fa fa-trash"></i> </button>';
            }

            $scope.loadInvoice = function(headerId){
                //alert(headerId);
                $state.go('app.view', {headerId: headerId});

            }
            $scope.deleteInvoice = function(headerId){
                //alert(headerId);
                $state.go('app.view', {headerId: headerId});

            }


}]);*/
