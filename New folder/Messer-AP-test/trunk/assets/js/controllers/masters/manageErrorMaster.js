'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('manageErrorMasterCtrl',function(DTOptionsBuilder, DTColumnBuilder,$scope, $http, $rootScope, $q,$compile,$cookies,ManageErrorMasterService){
	
	$scope.data={};
	var vm = this;
	 $scope.dtInstance={};
	 var serialNo=1;
	 vm.singleobj = {};
	 	 	  $scope.loginDetails={};
	 	  
	 	  
	  // to reload page
	  vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getErrorMasters').then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
		  
	      }   
	   
  	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
  		
          var defer = $q.defer();  
          
          $http.get($rootScope.ctx +'/getErrorMasters').then(function (result) {  
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
          DTColumnBuilder.newColumn('errorCode').withTitle('Error Code'),  
          DTColumnBuilder.newColumn('errorDesc').withTitle('Error Desc'),  
          DTColumnBuilder.newColumn('null').withTitle('Action').notSortable()
         .renderWith(actions),
          
      ];  
    
  	
      function actions(data, type, full) {
          vm.singleobj[full.errorId] = full;
                          
          return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="errorCtrl.edit(errorCtrl.singleobj['+full.errorId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="errorCtrl.deleteRow(errorCtrl.singleobj['+full.errorId+'])"></button></span>' ;
          

      }
      
      // to generate serialno
      function serialNoHtml() {
	    	return serialNo++;
	    }
	
	
      // to edit countries
      vm.edit=function(singleobj) {
     		                
	        	$scope.data=singleobj;
	        		      
	        }
      
      // to delete countries
      vm.deleteRow=function(singleobj){
     	 swal({
             title: "Deleting Record",
             text: "Are you sure want to  delete this State",
             type: "warning",
             showCancelButton: true,
             confirmButtonColor: "#007AFF",
             confirmButtonText: "Yes",
             closeOnConfirm: true
         }, function(isConfirm){
             if (isConfirm){
           	  //alert("hello");
            	 $http({
             		 url: $rootScope.ctx +'/deleteErrorMaster/'+singleobj.errorId,
             		 method: "DELETE",
             		 headers: {'Content-Type': 'application/json'}

             	 }).then(function onSuccess(response) {
             		 // setResponse("onSuccess",response);
             		 console.log("onSuccess",response);
             		 $("#saveMsg") .show().html('<div class="alert alert-success"<strong>deleted successfully</strong> </div>')
             		vm.reloadData();
             		$scope.clear();
             	 }).catch(function onError(response) {
             		vm.reloadData();
             		 console.log("onError",response);
             		 $("#saveMsg").show().html('<div class="alert alert-danger"<strong>delete failed</strong> </div>');

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
                //console.log("The form cannot be submitted because it contains validation errors!", "Errors are marked with a red, dashed border!");
                return;
            } else {

             $scope.loginDetails.userId=$cookies.get('userId');
			 $scope.loginDetails.tenantId=$cookies.get('tenantId');
			 $scope.loginDetails.tenantType=$cookies.get('tenantType');
			 ManageErrorMasterService.saveErrorMaster($scope.data,$scope.loginDetails).then(function(response){
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
	
	$scope.clear=function(){
		$scope.data={};
	}  
	
});

   
