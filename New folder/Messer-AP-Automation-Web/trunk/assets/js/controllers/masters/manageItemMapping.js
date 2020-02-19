'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('manageItemMappingCtrl', ["DTOptionsBuilder", "DTColumnBuilder","$q","ItemMappingService","$compile","$scope","$rootScope", "$http","$cookies", "$timeout", "$filter", function (DTOptionsBuilder, DTColumnBuilder,$q,ItemMappingService,$compile,$scope,$rootScope, $http,$cookies, $timeout,$filter) {
	
	$scope.data={};
	$scope.data.itemMasterDto={};


	  $scope.data.itemMappingDto={};
	    $scope.data.itemMappingDto1=[];
	var vm = this;
	vm.singleobj = {};
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
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getItemMapping/'+$scope.loginDetails.tenantId).then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	   }   
	   
 	  vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
          var defer = $q.defer();  
          $http.get($rootScope.ctx +'/getItemMapping/'+$scope.loginDetails.tenantId).then(function (result) {  
              defer.resolve(result.data);  
          });  
          return defer.promise;  
      }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
		  function createdRow(row, data, dataIndex) {
	          $compile(angular.element(row).contents())($scope);
	      }
  	
      vm.dtColumns = [  
          DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
          DTColumnBuilder.newColumn('itemMasterDto.itemName').withTitle('Item Name'),
          DTColumnBuilder.newColumn('itemMappingCode').withTitle('Vendor Item Name'),  
          DTColumnBuilder.newColumn('null').withTitle('Action').notSortable().renderWith(actions1),
      ];  
  	
      function actions1(data, type, full) {
          vm.singleobj[full.itemMappingId] = full;
          return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="ItemMappingCtrl.edit(ItemMappingCtrl.singleobj['+full.itemMappingId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="ItemMappingCtrl.deleteRow(ItemMappingCtrl.singleobj['+full.itemMappingId+'])"></button></span>' ;
      }
      
      function serialNoHtml() {
	    	return serialNo++;
	  }
	
      vm.edit=function(singleobj) {
	       $scope.data=singleobj;
	  }
      
      vm.deleteRow=function(singleobj){
     	 swal({
             title: "Deleting Record",
             text: "Are you sure want to  delete this Item Mapping",
             type: "warning",
             showCancelButton: true,
             confirmButtonColor: "#007AFF",
             confirmButtonText: "Yes",
             closeOnConfirm: true
         }, function(isConfirm){
             if (isConfirm){
            	 $http({
             		 url: $rootScope.ctx +'/deleteItemMapping/'+singleobj.itemMappingId,
             		 method: "DELETE",
             		 headers: {'Content-Type': 'application/json'}

             	 }).then(function onSuccess(response) {
             		 $("#saveMsg")
             		 .show()
             		 .html('<div class="alert alert-success"<strong>Item Mapping deleted successfully</strong> </div>')
             		 .fadeOut(5000);
             		 $scope.isDirty=false;
             		vm.reloadData();
             		$scope.clear();
             	 }).catch(function onError(response) {
             		vm.reloadData();
             		$scope.clear();
             		 $("#saveMsg").show().html('<div class="alert alert-danger"<strong>Item Mapping delete failed</strong> </div>');
             	 }); 

             }
         });   
     	 
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
  	                return;
  	            } else {
	  	             $scope.loginDetails.userId=$cookies.get('userId');
	  				 $scope.loginDetails.tenantId=$cookies.get('tenantId');
	  				 $scope.loginDetails.tenantType=$cookies.get('tenantType');
	  				 ItemMappingService.itemMapping($scope.data,$scope.loginDetails).then(function(response){
  					    vm.reloadData();
  						$scope.clear();
  						$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
					})
					.catch(function(response){
						$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
					});
				   form.$setPristine(true);
	            }

  	        }
  	    };
  	 $scope.uploadItemMapping=function(){
  		$scope.isexists={};
  		$scope.errormesage=[];
  	   	  $scope.loginDetails.userId=$cookies.get('userId');
  				 $scope.loginDetails.tenantId=$cookies.get('tenantId');
  				 $scope.loginDetails.tenantType=$cookies.get('tenantType');
  				$scope.data.itemMasterDto =  $rootScope.data1;
  				 if($scope.data.itemMasterDto.length==0){
  		        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+"File is empty"+'</strong> </div>').fadeOut(5000);
  		           }
  				 else {
  		                   angular.forEach($scope.data.itemMasterDto, function(value, key){
  		                			$scope.itemmaster = $filter('filter')($scope.itemList, {itemName: value.itemMasterDto})[0]; 
  							 
  					      if($scope.itemmaster){
  					        	  $scope.data.itemMappingDto1.push({itemMasterDto:$scope.itemmaster,itemMappingCode:value.itemMappingCode});
  					         }
  							         else{
  							        	$scope.errormesage.push(value.itemMasterDto.toString());
  							         }
  				   });
  		  
  		                 if($scope.errormesage.length>0) {
  		                	
  		                 swal({
  		                   title: "Item Codes",
  		                   text: $scope.errormesage.toString()+' '+'does not exists in Item master ,Still you want to save remaining records',
  		                   type: "warning",
  		                   showCancelButton: true,
  		                   confirmButtonColor: "#007AFF",
  		                   confirmButtonText: "Yes",
  		                   closeOnConfirm: true
  		               }, function(isConfirm){
  		                   if (isConfirm){
  		                	 ItemMappingService.saveExcelItemMapping($scope.data.itemMappingDto1,$scope.loginDetails).then(function(response){
	  		  						 vm.reloadData();
	  		  							$scope.clear();
	  		  							$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
  		                	 })
	  		  						.catch(function(response){
	  		  							$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
	  		  						});
  		                   }
  		               });  
  				 }else{
  		            	 ItemMappingService.saveExcelItemMapping($scope.data.itemMappingDto1,$scope.loginDetails).then(function(response){
		  						 vm.reloadData();
		  							$scope.clear();
		  							$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>');
		  						})
		  						.catch(function(response){
		  							$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
		  						});
  		             }
  				 }
  				 
  	     }
  	 $rootScope.$on('FILE_UPLOADED', function(event, originalEvent){
  	   	  
  	    alasql('SELECT [Item Name (Client)] AS [itemMasterDto], [Vendor Item Name] AS [itemMappingCode] FROM FILE(?,{headers:true,sheetid:"ItemMapping"})',[originalEvent],function(data){
  	        console.log("Data from file=",data);
  	        $rootScope.data1=data;
  	        
  	        
  	    });
  	});
   	 
	$scope.getItemList = function() {
	    $http({
	          method: 'GET',
	          url: $rootScope.ctx +'/getItemMaster',
	          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
	    }).then(function (response) {
	    	$scope.itemList=response.data
	 	    },function (error){
	        console.log(error, 'can not get Item list.');
	    });
     } 
	
	
	$scope.getItemList();
	
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
