'use strict';
/**
 * controllers for UI Bootstrap components
 */

app.controller('manageUqcMappingCtrl', ["DTOptionsBuilder", "DTColumnBuilder","$q","UqcMappingService","$compile","$scope","$rootScope", "$http","$cookies", "$timeout","$filter",  function (DTOptionsBuilder, DTColumnBuilder,$q,UqcMappingService,$compile,$scope,$rootScope, $http,$cookies, $timeout,$filter) {
	
	$scope.data={};
	 $scope.data.exceldata={};
    $scope.data.uQCMappingDto1=[];
    
	var vm = this;
	vm.singleobj = {};
	$scope.dtInstance={};
	var serialNo=1;
	$scope.loginDetails={};
	$scope.loginDetails.userId=$cookies.get('userId');
	$scope.loginDetails.tenantId=$cookies.get('tenantId');
	$scope.loginDetails.tenantType=$cookies.get('tenantType');
	$scope.loginDetails.parentTenantId=$cookies.get('parentTenantId');

	  
	  // to reload page
	  vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getUqcMapping/'+$scope.loginDetails.tenantId).then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	   }   
	   
 	  vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
          var defer = $q.defer();  
          $http.get($rootScope.ctx +'/getUqcMapping/'+$scope.loginDetails.tenantId).then(function (result) {  
              defer.resolve(result.data);  
          });  
          return defer.promise;  
      }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
		  function createdRow(row, data, dataIndex) {
	          $compile(angular.element(row).contents())($scope);
	      }
  	
      vm.dtColumns = [  
          DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),
          DTColumnBuilder.newColumn('uqcMasterDto.uqcCode').withTitle('UQC Code'),
          DTColumnBuilder.newColumn('uqcCode').withTitle('Vendor UQC Code'),  
          DTColumnBuilder.newColumn('null').withTitle('Action').notSortable().renderWith(actions1),
      ];  
  	
      function actions1(data, type, full) {
          vm.singleobj[full.uqcMappingId] = full;
          return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="uqcCtrl.edit(uqcCtrl.singleobj['+full.uqcMappingId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="uqcCtrl.deleteRow(uqcCtrl.singleobj['+full.uqcMappingId+'])"></button></span>' ;
      }
      
      function serialNoHtml() {
	    	return serialNo++;
	  }
	
      vm.edit=function(singleobj) {
    	   $("#saveMsg").show().html('');
	       $scope.data=singleobj;
	  }
      
      vm.deleteRow=function(singleobj){
     	 swal({
             title: "Deleting Record",
             text: "Are you sure want to  delete this Uqc Mapping",
             type: "warning",
             showCancelButton: true,
             confirmButtonColor: "#007AFF",
             confirmButtonText: "Yes",
             closeOnConfirm: true
         }, function(isConfirm){
             if (isConfirm){
            	 $http({
             		 url: $rootScope.ctx +'/deleteUqcMapping/'+singleobj.uqcMappingId,
             		 method: "DELETE",
             		 headers: {'Content-Type': 'application/json'}

             	 }).then(function onSuccess(response) {
             		 $("#saveMsg")
             		 .show()
             		 .html('<div class="alert alert-success"<strong>Uqc Mapping deleted successfully</strong> </div>')
             		 .fadeOut(5000);
             		 $scope.isDirty=false;
             		vm.reloadData();
             		$scope.clear();
             	 }).catch(function onError(response) {
             		vm.reloadData();
             		$scope.clear();
             		 $("#saveMsg").show().html('<div class="alert alert-danger"<strong>Uqc Mapping delete failed</strong> </div>');
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
  	            	 $("#saveMsg").show().html('');
	  				 UqcMappingService.uqcMapping($scope.data,$scope.loginDetails).then(function(response){
  					    vm.reloadData();
  						$scope.clear();
  						$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
					})
					.catch(function(response){
						$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>');
					});
				   form.$setPristine(true);
	            }

  	        }
  	    };
  	 $scope.uploadUqcMapping=function(){
  		$scope.data.uQCMappingDto1=[];
   	         $scope.loginDetails.userId=$cookies.get('userId');
			 $scope.loginDetails.tenantId=$cookies.get('tenantId');
			 $scope.loginDetails.tenantType=$cookies.get('tenantType');
	
			// var name=$scope.sheetname;
			 $scope.data.exceldata = $scope.data1;
			 
			
			var errormsgs=[];
			
			 if($scope.data.exceldata.length==0){
	        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+"File is empty"+'</strong> </div>').fadeOut(5000);
	        	   
	           }
			
			 else {
				 $scope.uqcList;
							 
			 angular.forEach($scope.data.exceldata, function(value, key){
					 
				 //$scope.dtos={};					 
						
						         
							$scope.dtos = $scope.uqcList.filter(function(uqc) {
								return uqc.uqcCode == value.uqcMasterDto;
							});

						 
											 
			 if($scope.dtos.length>0){
				 $scope.data.uQCMappingDto1.push({"uqcMasterDto":$scope.dtos[0],"uqcCode":value.uqcCode});
				 }
			 else{
				 errormsgs.push(value.uqcMasterDto);
			 }	
				  		 
			   });
				 
			 }
			 if(errormsgs){
				 swal({
		             title: "UQC Codes",
		             text: errormsgs+" "+"Doesnt exist in UQC Master,Click on confirm to save Remaining Data",
		             type: "warning",
		             showCancelButton: true,
		             confirmButtonColor: "#007AFF",
		             confirmButtonText: "Yes",
		             closeOnConfirm: true
		         }, function(isConfirm){
		             if(isConfirm){
		            	 UqcMappingService.saveExcelUqcMapping($scope.data.uQCMappingDto1,$scope.loginDetails).then(function(response){
							 vm.reloadData();
								$scope.clear();
								$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
							})
							.catch(function(response){
								$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>').fadeOut(5000);
							}); 
		             }
		         });   
				 
			 }
			 
			 else{
				 UqcMappingService.saveExcelUqcMapping($scope.data.uQCMappingDto1,$scope.loginDetails).then(function(response){
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
   	  
 	    alasql('SELECT [Standard UQC Code(Client)] AS [uqcMasterDto], [Vendor UQC Code] AS [uqcCode] FROM FILE(?,{headers:true,sheetid:"UQC Mapping"})',[originalEvent],function(data){
 	    	 //console.log("Data from file=",data);
 	        $scope.data1=data;
 	        
 	        
 	    });
 	});
  	 
      
	$scope.getUqcList = function() {
	    $http({
	          method: 'GET',
	          url: $rootScope.ctx +'/getVendorUqcList/'+$scope.loginDetails.parentTenantId,
	          headers: {'loginDetails':JSON.stringify($scope.loginDetails)}
	    }).then(function (response) {
	    	$scope.uqcList=response.data
	    	console.log($scope.uqcList)
	 	    },function (error){
	        console.log(error, 'can not get uqc list.');
	    });
     } 
	
	
	$scope.getUqcList();
	
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

