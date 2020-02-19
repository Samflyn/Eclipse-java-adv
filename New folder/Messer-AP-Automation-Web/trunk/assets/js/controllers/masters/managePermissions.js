'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('managePermissionsCtrl',['PermissionMasterService','$cookies','$q','$compile','DTOptionsBuilder', 'DTColumnBuilder','$scope','$http','$window', '$rootScope',function(PermissionMasterService,$cookies,$q,$compile,DTOptionsBuilder, DTColumnBuilder,$scope,$http,$window, $rootScope) {
	
	    $scope.data ={};
	 
		var vm = this;
		vm.singleRecord={};
		$scope.dtInstance={};
		$scope.loginDetails={};
		
		var serialNo=1;	
		
		vm.dtOptions = DTOptionsBuilder.fromFnPromise(function (){
			var defer = $q.defer();  
			console.log("after defer()");
	        $http.get($rootScope.ctx +'/getPermissionsList').then(function (result) { 
	            defer.resolve(result.data);
	        });  
	        return defer.promise;  
	    }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
			function createdRow(row,data,dataIndex)
		{
			$compile(angular.element(row).contents())($scope);
		}
		
		vm.displayTable=true;
	    vm.dtColumns=
	    	[
		DTColumnBuilder.newColumn('null').withTitle('SNO').renderWith(serialNoHtml),
		DTColumnBuilder.newColumn('permissionName').withTitle('Permission Name'),
		DTColumnBuilder.newColumn('permissionDesc').withTitle('Permission Description'),
		DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable() 
			          .renderWith(actionsHtml)
		
		];
	    
	    
	    function serialNoHtml()
	    {
	    	return serialNo++;
	    }
	    
	  function actionsHtml(data, type, full, meta)
	    {
	    	
	    	  vm.singleRecord[full.permissionId]=full;
	    	  return '<span class="form-group"><button  type="button"  class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="permissionMasterCtrl.edit(permissionMasterCtrl.singleRecord['+full.permissionId+'])"></button></span>  <span class="form-group"><button type="button"  class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" autocomplete="off" title="Delete" id="delete" ng-click="permissionMasterCtrl.deletePermission(permissionMasterCtrl.singleRecord['+full.permissionId+'])"></button></span>' ;
	    }
	  vm.edit=function(singleObj)
	    {
		  vm.seletedRawRecord=singleObj;	
		  $scope.data=singleObj;
	    }
	
	  
	   vm.deletePermission=function(singleObj){
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
			        url:$rootScope.ctx +'/deletePermission/'+singleObj.permissionId
					}).then(function onSuccess(response) {
						vm.reloadData();
					$("#saveMsg")
	        	   .show()
	        	   .html('<div class="alert alert-success"<strong>'+'deleted successfully'+'</strong> </div>')
	        	   .fadeOut(3000);
					
					}, function(err) {
						
						$("#saveMsg")
			        	   .show()
			        	   .html('<div class="alert alert-success"<strong>Failed</strong> </div>')
			        	   .fadeOut(3000);
											})
   	  }
      });
		} 
	
	
	 $scope.submitData=function()
	 {
			$scope.loginDetails.userId=$cookies.get('userId');
			$scope.loginDetails.tenantId=$cookies.get('tenantId');
			$scope.loginDetails.tenantType=$cookies.get('tenantType');
			PermissionMasterService.savePermissionMaster($scope.data,$scope.loginDetails).then(function(response){
				vm.reloadData();
				$scope.clear();
               $("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
           })
           .catch(function(response){
        	   console.log(response.data.errorMessage)
                $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
           });
	 }
	 
	 vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getPermissionsList').then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	      } 

$scope.clear=function(){
	$scope.data={};
	
}
}]); 