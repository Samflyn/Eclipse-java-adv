'use strict';
/**
 * controllers for UI Bootstrap components
 */


app.controller('manageItemMasterCtrl',['ItemMasterService','$cookies','$q','$compile','DTOptionsBuilder', 'DTColumnBuilder','$scope','$http','$window', '$rootScope','$parse',function(ItemMasterService,$cookies,$q,$compile,DTOptionsBuilder, DTColumnBuilder,$scope,$http,$window, $rootScope) {
	
	    $scope.data ={};
	   $scope.data.exceldata = [];
	   $scope.itemList=[];
		var vm = this;
		vm.singleRecord={};
		$scope.dtInstance={};
		$scope.loginDetails={};
		
		 $scope.excel;
		var serialNo=1;	
		
		vm.dtOptions = DTOptionsBuilder.fromFnPromise(function (){
			var defer = $q.defer();  
			console.log("after defer()");
			var tenantId=$cookies.get('tenantId');
			 $http({
		          method: 'GET',
		          url: $rootScope.ctx +'/getItems/'+tenantId,
		          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
		    }).then(function (result) { 
	            defer.resolve(result.data);
	            $scope.itemList=result.data;
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
		DTColumnBuilder.newColumn('itemName').withTitle('Item Name'),
		DTColumnBuilder.newColumn('itemDesc').withTitle('Item Description'),
		DTColumnBuilder.newColumn('itemType').withTitle('Item Type'),
		DTColumnBuilder.newColumn('hsnCode').withTitle('HsnCode'),
		
		DTColumnBuilder.newColumn('null').withTitle('Actions').notSortable() 
			          .renderWith(actionsHtml)
		
		];
	    
	    
	    function serialNoHtml()
	    {
	    	return serialNo++;
	    }
	    
	  function actionsHtml(data, type, full, meta)
	    {
	    	
	    	  vm.singleRecord[full.itemId]=full;
	    	  return '<span class="form-group"><button  type="button"  class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="ItemMasterCtrl.edit(ItemMasterCtrl.singleRecord['+full.itemId+'])"></button></span>  <span class="form-group"><button type="button"  class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" autocomplete="off" title="Delete" id="delete" ng-click="ItemMasterCtrl.deleteItem(ItemMasterCtrl.singleRecord['+full.itemId+'])"></button></span>' ;
	    }
	  vm.edit=function(singleObj)
	    {
		  vm.seletedRawRecord=singleObj;	
		  $scope.data=singleObj;
	    }
	

	  //to delete data
	  vm.deleteItem=function(singleObj){
		  console.log(singleObj.itemId)
		  var id=singleObj.itemId;
		  swal({
			  title: "Deleting Record",
			  text: "Are you sure you want to delete this Item",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#007AFF",
			  confirmButtonText: "Yes",
			  closeOnConfirm: true
		  }, function(isConfirm){
			  if (isConfirm){
				  $http({
					  url: $rootScope.ctx +'/deleteItem/'+singleObj.itemId,
					  method: "DELETE",
					 // params:{"itemId":id},
					  headers: {'Content-Type': 'application/json'}

				  }).then(function onSuccess(response) {
					  // setResponse("onSuccess",response);
					  console.log("onSuccess",response);
					   $scope.msg=response.data;
					  $("#saveMsg")
					  .show()
					  .html('<div class="alert alert-success"<strong>'+response.data+'</strong> </div>')
					  .fadeOut(5000);
					  $scope.isDirty=false;
					  $scope.clear();
					  vm.reloadData();

				  }).catch(function onError(response) {
					  $scope.clear();
					  vm.reloadData();
					  console.log("onError",response);
					  $("#saveMsg")
					  .show()
					  .html('<div class="alert alert-danger"<strong>delete failed</strong> </div>')
					  .fadeOut(5000);

				  }); 

			  }
		  });   
	  }
	  
	  $scope.uploadItems=function(){
		  $scope.data.itemMasterDTOs1 = [];
		  $scope.loginDetails.userId=$cookies.get('userId');
			$scope.loginDetails.tenantId=$cookies.get('tenantId');
			$scope.loginDetails.tenantType=$cookies.get('tenantType');
			
			$scope.data.exceldata= $scope.data1;
				 
           if($scope.data.exceldata.length==0){
        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+"File is empty"+'</strong> </div>').fadeOut(5000);
        	   
           }
            else {
            	 angular.forEach($scope.data.exceldata, function(value, key){
     				$scope.dtos={};					 
     					
     					
     				
     				$scope.dtos =  $scope.itemList.filter(function(item) {
     					return item.itemName == value.itemName;
     				});
     					 if( $scope.dtos.length>0){
     						 $scope.dtos[0].itemDesc=value.itemDesc;
     						$scope.dtos[0].itemType=value.itemType;
     						 $scope.dtos[0].hsnCode=value.hsnCode;
     						 $scope.dtos[0].rate=value.rate;
     						 $scope.data.itemMasterDTOs1.push($scope.dtos[0]);
     						
     					 }
     					 else{
     						 $scope.data.itemMasterDTOs1.push(value);
     					 }
     					 
     				 });
     				 
     	ItemMasterService.excelItemMaster( $scope.data.itemMasterDTOs1,$scope.loginDetails).then(function(response){
					 vm.reloadData();
						$scope.clear();
						$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
					})
					.catch(function(response){
						$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>').fadeOut(5000);
					});
           }
         
	        
      }
	 
	  
	  $rootScope.$on('FILE_UPLOADED', function(event, originalEvent){
    	  
  	    alasql('SELECT [Item Name] AS [itemName],[Item Description ] AS [itemDesc],[Item Type] AS [itemType],[HSN/SAC] AS [hsnCode] ,[Tax Rate] AS [rate] FROM FILE(?,{headers:true,sheetid:"Item"})',[originalEvent],function(data){
  	        console.log("Data from file=",data);
  	        $scope.data1=data;
  	        
  	        
  	    });
  	});
	  
	  
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
	            	$scope.loginDetails.userId=$cookies.get('userId');
	    			$scope.loginDetails.tenantId=$cookies.get('tenantId');
	    			$scope.loginDetails.tenantType=$cookies.get('tenantType');
	    			ItemMasterService.saveItemMaster($scope.data,$scope.loginDetails).then(function(response){
	    				
	                   $("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
	                   vm.reloadData();
	    				$scope.clear();
	               })
	               .catch(function(response){
	            	   console.log(response.data.errorMessage)
	                    $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
	               });
	    			form.$setPristine(true); 
		            }

	        }
	    };
	
	
	
	 
	 vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
			   var tenantId=$cookies.get('tenantId');
	    		  var defer = $q.defer();  
	    		  $http({
			          method: 'GET',
			          url: $rootScope.ctx +'/getItems/'+tenantId,
			          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
			    })
			        .then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	      } 

$scope.clear=function(){
	$scope.data={};
	
}
}]); 



app.directive('nngFileUpload', ['$rootScope', function ($rootScope) {
    return {
        restrict: 'A',
        scope: {},
        link: function (scope, elm, attrs) {
            //elm should be input field else find input field(elm.find("input[type='file']"))
            elm.on('change', function (event) {
                $rootScope.$emit("FILE_UPLOADED", event.originalEvent);
            });
        }
    }
}]);
