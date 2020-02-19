'use strict';
/**
 * controllers for UI Bootstrap components
 */


app.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;
          
          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
 }]);

app.controller('manageUqcCtrl', ["DTOptionsBuilder", "DTColumnBuilder","$q","$compile","$scope","$rootScope", "$http","$cookies", "UqcMasterService", "$timeout", '$parse','$filter', function (DTOptionsBuilder, DTColumnBuilder,$q,$compile,$scope,$rootScope, $http,$cookies, UqcMasterService, $timeout,$filter) {
	
	$scope.data={};
	 $scope.data.uQCMasterDto = [];
	 $scope.uqcList=[];
	
	var vm = this;
	 vm.uqc = {};
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
			        $http.get($rootScope.ctx +'/getUqcList/'+$scope.loginDetails.tenantId).then(function (result) { 
			            defer.resolve(result.data);
			            $scope.uqcList=result.data;
			        });  
			        return defer.promise;  
	          }, true);
	      }   
	   
 	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
  		
          var defer = $q.defer();  
          
          $http.get($rootScope.ctx +'/getUqcList/'+$scope.loginDetails.tenantId).then(function (result) {  
              defer.resolve(result.data);  
                           
             		console.log(result.data)	
             		 $scope.uqcList=result.data;
          });  
          return defer.promise;  
      }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
		  function createdRow(row, data, dataIndex) {
	          // Recompiling so we can bind Angular directive to the DT
	          $compile(angular.element(row).contents())($scope);
	      };  
  	
      vm.dtColumns = [  
      	  DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),  
          DTColumnBuilder.newColumn('uqcCode').withTitle('UQC Code'),  
          DTColumnBuilder.newColumn('uqcDesc').withTitle('UQC Desc'),
          DTColumnBuilder.newColumn('null').withTitle('Action').notSortable()
         .renderWith(actions1),
          
      ];  
    
  	
      function actions1(data, type, full) {
          vm.uqc[full.uqcMasterId] = full;
                          
          return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="uqcCtrl.edit(uqcCtrl.uqc['+full.uqcMasterId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="uqcCtrl.deleteRow(uqcCtrl.uqc['+full.uqcMasterId+'])"></button></span>' ;
          

      }
      
      // to generate serialno
      function serialNoHtml() {
	    	return serialNo++;
	    }
	
	
      // to edit countries
      vm.edit=function(uqc) {
	        	$scope.data=uqc;
	        }
      
      // to delete countries
      vm.deleteRow=function(uqc){
     	 var id=uqc.uqcMasterId;
     	 swal({
             title: "Deleting Record",
             text: "Are you sure want to  delete this Uqc",
             type: "warning",
             showCancelButton: true,
             confirmButtonColor: "#007AFF",
             confirmButtonText: "Yes",
             closeOnConfirm: true
         }, function(isConfirm){
             if (isConfirm){
           	  //alert("hello");
            	 $http({
             		 url: $rootScope.ctx +'/deleteUqc/'+id,
             		 method: "DELETE",
             		 headers: {'Content-Type': 'application/json'}

             	 }).then(function onSuccess(response) {
             		 // setResponse("onSuccess",response);
             		 console.log("onSuccess",response);
             		 $("#saveMsg")
             		 .show()
             		 .html('<div class="alert alert-success"<strong>deleted successfully</strong> </div>')
             		 .fadeOut(5000);
             		 $scope.isDirty=false;
             		vm.reloadData();
             		$scope.clear();
             	 }).catch(function onError(response) {
             		vm.reloadData();
             		$scope.clear();
             		 console.log("onError",response);
             		 $("#saveMsg")
             		 .show()
             		 .html('<div class="alert alert-danger"<strong>delete failed</strong> </div>')
             		 .fadeOut(5000);

             	 }); 

             }
         });   
     	
     	
     	 
      }   
      $scope.uploadUqc=function(){
    	 
    	  $scope.loginDetails.userId=$cookies.get('userId');
			 $scope.loginDetails.tenantId=$cookies.get('tenantId');
			 $scope.loginDetails.tenantType=$cookies.get('tenantType');
	          
			
			 $scope.data.exceldata =  $scope.data1;
			 $scope.data.uQCMappingDto1=[];
			 
			 
			 if($scope.data.exceldata.length==0){
	        	   $("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+"File is empty"+'</strong> </div>').fadeOut(5000);
	        	   
	           }
			 else {
				 angular.forEach($scope.data.exceldata, function(value, key){
				//$scope.dtos={};					 
					
				$scope.dtos =  $scope.uqcList.filter(function(uqc) {
					return uqc.uqcCode == value.uqcCode;
				});
		 if($scope.dtos.length>0){
						 $scope.dtos[0].uqcDesc=value.uqcDesc;
						 $scope.data.uQCMappingDto1.push($scope.dtos[0]);
						
					 }
					 else{
						 $scope.data.uQCMappingDto1.push(value);
					 }
					 
				 });
				 
				
				 
  			UqcMasterService.excelSaveUqc( $scope.data.uQCMappingDto1,$scope.loginDetails).then(function(response){
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
    	  
    	    alasql('SELECT [UQC Code] AS [uqcCode],[UQC Description] AS [uqcDesc] FROM FILE(?,{headers:true,sheetid:"UQC"})',[originalEvent],function(data){
    	        //console.log("Data from file=",data);
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
  	                //console.log("The form cannot be submitted because it contains validation errors!", "Errors are marked with a red, dashed border!");
  	                return;
  	            } else {

  	             $scope.loginDetails.userId=$cookies.get('userId');
  				 $scope.loginDetails.tenantId=$cookies.get('tenantId');
  				 $scope.loginDetails.tenantType=$cookies.get('tenantType');
  				UqcMasterService.uqcMaster($scope.data,$scope.loginDetails).then(function(response){
  					 vm.reloadData();
  						$scope.clear();
  						$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
  					})
  					.catch(function(response){
  						$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>').fadeOut(5000);
  					});
  	        form.$setPristine(true);
  		            }

  	        }
  	    };
      
  
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
