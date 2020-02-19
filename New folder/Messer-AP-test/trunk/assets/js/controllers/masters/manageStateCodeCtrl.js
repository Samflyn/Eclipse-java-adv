'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller('manageStateCodeCtrl',function(DTOptionsBuilder, DTColumnBuilder,$scope, $http, $rootScope, $q,$compile,$cookies,ManageStateService){
	
	$scope.data1={};
	var vm = this;
	 $scope.dtInstance={};
	 var serialNo=1;
	 vm.states = {};
	 	 	  $scope.loginDetails={};
	 	  
	 	  
	  // to reload page
	  vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getAllStates').then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
		  
	      }   
	   
  	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
  		
          var defer = $q.defer();  
          
          $http.get($rootScope.ctx +'/getAllStates').then(function (result) {  
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
          DTColumnBuilder.newColumn('stateCode').withTitle('State Code'),  
          DTColumnBuilder.newColumn('stateName').withTitle('State Name'),  
          DTColumnBuilder.newColumn('null').withTitle('Action').notSortable()
         .renderWith(actions1),
          
      ];  
    
  	
      function actions1(data, type, full) {
          vm.states[full.stateId] = full;
                          
          return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="stateCtrl.edit(stateCtrl.states['+full.stateId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" id="delete" ng-click="stateCtrl.deleteRow(stateCtrl.states['+full.stateId+'])"></button></span>' ;
          

      }
      
      // to generate serialno
      function serialNoHtml() {
	    	return serialNo++;
	    }
	
	
      // to edit countries
      vm.edit=function(state) {
     		                
	        	$scope.data1=state;
	        		      
	        }
      
      // to delete countries
      vm.deleteRow=function(state){
     	 var id=state.stateId;
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
             		 url: $rootScope.ctx +'/deleteState/'+id,
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
			 ManageStateService.saveState($scope.data1,$scope.loginDetails).then(function(response){
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
		$scope.data1={};
	}  
	
});

   
