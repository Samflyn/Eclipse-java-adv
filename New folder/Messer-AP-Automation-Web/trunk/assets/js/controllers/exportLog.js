app.controller("exportLogCtrl",["DTOptionsBuilder", "DTColumnBuilder", "$q","$scope","$http","$compile", "$rootScope","$cookies",function(DTOptionsBuilder, DTColumnBuilder, $q,$scope,$http,$compile, $rootScope,$cookies){
	
$scope.data1={};
	var vm = this;
	 vm.persons = {};
	  $scope.dtInstance={};
	  var serialNo=1;
	  $scope.loginDetails={};
		$scope.loginDetails.userId=$cookies.get('userId');
		$scope.loginDetails.tenantId=$cookies.get('tenantId');
		$scope.loginDetails.tenantType=$cookies.get('tenantType');
	 
	  // to reload page
	  vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getExportLogByTenantId/'+$cookies.get('tenantId')).then(function (result) { 
			            defer.resolve(result.data);
			            console.log(result.data)
			        });  
			        return defer.promise;  
	          }, true);
	      }   
	   
  	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
          var defer = $q.defer();  
          $http.get($rootScope.ctx +'/getExportLogByTenantId/'+$cookies.get('tenantId')).then(function (result) {  
              defer.resolve(result.data);  
             		console.log(result.data)																																																																																																																																																						
          });  
          return defer.promise;  
      }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
		  function createdRow(row, data, dataIndex) {
	          // Recompiling so we can bind Angular directive to the DT
	          $compile(angular.element(row).contents())($scope);
	      };  
      vm.dtColumns = [  
          DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),  
          DTColumnBuilder.newColumn('fileName').withTitle('File Name'),  
          DTColumnBuilder.newColumn('exportTime').withTitle('Exported Time'),  
          DTColumnBuilder.newColumn('exportThrough').withTitle('Export Through')
      ];  
      function serialNoHtml() {
	    	return serialNo++;
	    }
	$scope.clear=function(){
		$scope.data1={};
	
	}  
	
}]);