
app.controller('viewBulkStatusQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$compile","DTOptionsBuilder","DTColumnBuilder","viewBulkStatusQueueService", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$compile,DTOptionsBuilder, DTColumnBuilder,viewBulkStatusQueueService,$timeout,$stateParams,$window) {
	$scope.data ={};
	 $scope.dtInstance={};
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
    $scope.invoiceType= $stateParams.invoiceType;
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	var vm= this;
	 vm.singleRecord={};

	 var tableIndex = "0";	
	
	
		   vm.dtOptions = DTOptionsBuilder
	        .newOptions()
	        .withDataProp('data')
	        .withOption('processing', true)
	        .withOption('serverSide', true)
	        .withOption('ajax', {
			     url: $rootScope.ctx + "/bulkupload/getBulkUploadStatus/"+$scope.loginDetails.userId+"/"+"OPEN"+"/"+$scope.invoiceType,
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
				  DTColumnBuilder.newColumn('null').withTitle('S.No').notSortable().renderWith(serialNoHtml),
			        DTColumnBuilder.newColumn('fileName').withTitle('File Name'),
			        DTColumnBuilder.newColumn('fileSize').withTitle('FileSize').renderWith(toappend), 
			        DTColumnBuilder.newColumn('uploaderName').withTitle('Uploaded By'),  
			        DTColumnBuilder.newColumn('uploadedOn').withTitle('Uploaded On'),  
			      	 DTColumnBuilder.newColumn('status').withTitle('Status')
			    ];
		 
		 
		 
		 
		   function createdRow(row, data, dataIndex) {
		        $compile(angular.element(row).contents())($scope);
		    }
		 
		 
	    function serialNoHtml() {
	    	return ++tableIndex;
	    }
	    
	 
	   function toappend(data, type, full, meta){
		   return full.fileSize+" KB";
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
  	  $scope.toFailed = function(){$scope.$broadcast('failed-reload','');}
  	  $scope.toOpen = function(){$scope.dtInstance.reloadData();}
  	  
  	  
  	  
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }

}]);




app.controller('completedBulkStatusQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$compile","DTOptionsBuilder","DTColumnBuilder","viewBulkStatusQueueService", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$compile,DTOptionsBuilder, DTColumnBuilder,viewBulkStatusQueueService,$timeout,$stateParams,$window) {
	$scope.data ={};
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	   $scope.invoiceType= $stateParams.invoiceType;
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	
		   vm.dtOptions = DTOptionsBuilder
	        .newOptions()
	        .withDataProp('data')
	        .withOption('processing', true)
	        .withOption('serverSide', true)
	        .withOption('ajax', {
			     url: $rootScope.ctx + "/bulkupload/getBulkUploadStatus/"+$scope.loginDetails.userId+"/"+"COMPLETED"+"/"+$scope.invoiceType,
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
				  DTColumnBuilder.newColumn('null').withTitle('S.No').notSortable().renderWith(serialNoHtml),
			        DTColumnBuilder.newColumn('fileName').withTitle('File Name'),
			        DTColumnBuilder.newColumn('fileSize').withTitle('FileSize').renderWith(toappend), 
			        DTColumnBuilder.newColumn('uploaderName').withTitle('Uploaded By'),  
			        DTColumnBuilder.newColumn('uploadedOn').withTitle('Uploaded On'),  
			      	 DTColumnBuilder.newColumn('status').withTitle('Status')
			    ];
		 
		 
		 
		 
		   function createdRow(row, data, dataIndex) {
		        $compile(angular.element(row).contents())($scope);
		    }
		 
		 
	    function serialNoHtml() {
	    	return ++tableIndex;
	    }
	    
	 
	   function toappend(data, type, full, meta){
		   return full.fileSize+" KB";
	   }
	   
	   $scope.$on('completed-reload',function(e,args){
		   $scope.dtInstance.reloadData();
		})
	 
 		
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
	
 
}]);

app.controller('failedBulkStatusQueueCtrl', ["$scope","$rootScope", "$http","$q","$cookies","$filter","$compile","DTOptionsBuilder","DTColumnBuilder","viewBulkStatusQueueService", "$timeout",  "$stateParams","$window",function ($scope,$rootScope,$http,$q,$cookies,$filter,$compile,DTOptionsBuilder, DTColumnBuilder,viewBulkStatusQueueService,$timeout,$stateParams,$window) {
	$scope.data ={};
	
	$scope.loginDetails={};
    $scope.data.tenantDto;
    $scope.params={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 var tenantId=$cookies.get('tenantId')
	   $scope.invoiceType= $stateParams.invoiceType;
	var vm= this;
	 vm.singleRecord={};
	 $scope.dtInstance={};
	 var tableIndex = "0";	
	
	
		   vm.dtOptions = DTOptionsBuilder
	        .newOptions()
	        .withDataProp('data')
	        .withOption('processing', true)
	        .withOption('serverSide', true)
	        .withOption('ajax', {
			     url: $rootScope.ctx + "/bulkupload/getBulkUploadStatus/"+$scope.loginDetails.userId+"/"+"FAILED"+"/"+$scope.invoiceType,
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
				  DTColumnBuilder.newColumn('null').withTitle('S.No').notSortable().renderWith(serialNoHtml),
			        DTColumnBuilder.newColumn('fileName').withTitle('File Name'),
			        DTColumnBuilder.newColumn('fileSize').withTitle('FileSize').renderWith(toappend), 
			        DTColumnBuilder.newColumn('uploaderName').withTitle('Uploaded By'),  
			        DTColumnBuilder.newColumn('uploadedOn').withTitle('Uploaded On'),  
			      	 DTColumnBuilder.newColumn('status').withTitle('Status'),
			         DTColumnBuilder.newColumn(null).withTitle('').notSortable()
		 	            .renderWith(actionsHtml).withOption('width', '70px')
			    ];
		 
		   function createdRow(row, data, dataIndex) {
		        
		        $compile(angular.element(row).contents())($scope);
		    }
		 
		 
	    function serialNoHtml() {
	    
	    	return ++tableIndex;
	    }
	    
		   function toappend(data, type, full, meta){
		   return full.fileSize+" KB";
	   }
		   vm.uqc={};
	   function actionsHtml(data, type, full, meta) {
		    vm.uqc[full.id] = full;	    	
		    return '<button data-toggle="tooltip" data-placement="bottom" title="Reason to Fail" class="btn btn-primary" ng-click="editDetails(showCase.uqc['+full.id+'])" >' +
	            '   <i class="fa fa-eye"></i>' +                                                                                   
	            '</button> <button data-placement="bottom" title="Retry" class="btn btn-warning" ng-click=\'retryOCR(' +JSON.stringify(full)+ ')\' >' +
	            '  <i class="fa fa-repeat"></i>' +
	            '</button> ';
	    }
	   $scope.$on('failed-reload',function(e,args){
	    	$scope.dtInstance.reloadData();
		})
	 
		   $scope.editDetails = function(obj){
		   $scope.reason=obj.reason;
		   $('#myModal').modal();
	   }
 		
     $scope.clear = function(){
		   $scope.data ={};
	   $scope.myForm.$submitted=false;
	   }
     $scope.retryOCR = function(fileDetails){
    	 $http({
    		    method: 'POST',
    		    url: $rootScope.ctx +'/retryOCR',
    		    data: fileDetails,
    		    headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
    		   }).then(function(response) {
    			   $scope.dtInstance.reloadData();
    		 	}).catch(function onError(response){
    		 		$scope.dtInstance.reloadData();
    		 		
    		   });
		  console.log("retry",fileDetails);
	   }
	
 
}]);



