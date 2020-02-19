'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller("manageBusinessPartnerCtrl",['DTOptionsBuilder', 'DTColumnBuilder', '$q','$rootScope','$scope','$http','$timeout','$compile','$window',PartnersCtrl]);
function PartnersCtrl (DTOptionsBuilder,DTColumnBuilder,$q,$rootScope,$scope,$http,$timeout,$compile,$window)
{

	$scope.data={};
	var vm = this;
	vm.singleRecord={};
	$scope.dtInstance={};
	
	var serialNo=1;	
	
	vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getBusinessPartners').then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	      } 
	
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function (){
	
		var defer = $q.defer();  
		console.log("after defer()");
        $http.get($rootScope.ctx +'/getBusinessPartners').then(function (result) { 
        
            defer.resolve(result.data);
        });  
        return defer.promise;  
    }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	
	vm.displayTable=true;
    vm.dtColumns=
    	[
    		
	DTColumnBuilder.newColumn('null').withTitle('SNO').renderWith(serialNoHtml),
	DTColumnBuilder.newColumn('bpCode').withTitle('Partner Code'),
	DTColumnBuilder.newColumn('bpName').withTitle('Partner Name'),
	DTColumnBuilder.newColumn('bpRegisteredId').withTitle('RegisteredId'),
	DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable() 
		          .renderWith(actionsHtml)
	
	];
    
    
    function serialNoHtml()
    {
    	return serialNo++;
    }
    
  function actionsHtml(data, type, full, meta)
    {
    	  vm.singleRecord[full.bpId]=full;
    	  console.log("vm.singleRecord",vm.singleRecord)
    	  return '<span class="form-group"><button  type="button"  class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="PartnersCtrl.edit(PartnersCtrl.singleRecord['+full.bpId+'])"></button></span>  <span class="form-group"><button type="button" class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" autocomplete="off" title="Delete" id="delete" ng-click="PartnersCtrl.deletePartner(PartnersCtrl.singleRecord['+full.bpId+'])"></button></span>' ;
    }
  vm.edit=function(singleObj)
    {
	  vm.seletedRawRecord=singleObj;	
	  $scope.data=singleObj;
    }

vm.deletePartner=function(singleObj)
{
	$http({
		method:'POST',
		url:$rootScope.ctx +'/deletePartner',
		headers: {'Content-Type': 'application/json' },
		params:{"bpId":singleObj.bpId},
	}).then(function onSuccess(response){
		$("#saveMsg")
    	   .show()
    	   .html('<div class="alert alert-success"<strong>deleted successfully</strong> </div>')
    	   .fadeOut(3000);
		vm.reloadData();
	}, function(err) {
		$("#saveMsg")
    	   .show()
    	   .html('<div class="alert alert-success"<strong>Failed to delete</strong> </div>')
    	   .fadeOut(3000);
	}
			);
}


$scope.submitData=function()
{

$http({
    method: 'POST',
    url: $rootScope.ctx +'/saveBusinessPartner',
    data: $scope.data,
    headers: {'Content-Type': 'application/json' }
	}).then(function onSuccess(response) {
		console.log("response {}",response.data)
		$("#saveMsg")
    	   .show()
    	   .html('<div class="alert alert-success"<strong>saved successfully</strong> </div>')
    	   .fadeOut(3000);
		vm.reloadData();
		$scope.clear();
}, function(err) {
	location.reload();
	$("#saveMsg")
	   .show()
	   .html('<div class="alert alert-success"<strong>Failed</strong> </div>')
	   .fadeOut(3000);
});

}
$scope.clear=function(){
	$scope.data={};
}  


}
