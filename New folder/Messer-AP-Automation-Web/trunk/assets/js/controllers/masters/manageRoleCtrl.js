'use strict';
/**
 * controllers for UI Bootstrap components
 */
app.controller("manageRoleCtrl",["DTOptionsBuilder", "DTColumnBuilder","$q","$scope","$http","$compile", "$rootScope","$cookies","ManageRoleService",function(DTOptionsBuilder, DTColumnBuilder, $q,$scope,$http,$compile, $rootScope,$cookies,ManageRoleService){

$scope.data1={};

	var vm = this;
	 vm.persons = {};
	  $scope.dtInstance={};
	  var serialNo=1;
	
	  $scope.loginDetails={};
	  
	
	  //to reload page
	  vm.reloadData=function() {
		   serialNo=1;
		   $scope.dtInstance.reloadData(function(){
	    		  console.log('reload')
	    		  var defer = $q.defer();  
			        $http.get($rootScope.ctx +'/getAllUserRoles').then(function (result) { 
			            defer.resolve(result.data);
			        });  
			        return defer.promise;  
	          }, true);
	      }   
	   
   	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {  
   		
           var defer = $q.defer();  
           
           $http.get($rootScope.ctx +'/getAllUserRoles').then(function (result) {  
               defer.resolve(result.data);  
                            
              		console.log(result.data)																																																																																																																																																						
           });  
           return defer.promise;  
       }).withPaginationType('full_numbers').withOption('createdRow', createdRow); 
		  function createdRow(row, data, dataIndex) {
	          // Recompiling so we can bind Angular directive to the DT
	          $compile(angular.element(row).contents())($scope);
	      }
   	
       vm.dtColumns = [  
       	
           DTColumnBuilder.newColumn('null').withTitle('S.No').renderWith(serialNoHtml),  
           DTColumnBuilder.newColumn('roleName').withTitle('Role Name'),  
           DTColumnBuilder.newColumn('roleDesc').withTitle('Role Description'),  
         /*  DTColumnBuilder.newColumn('null').withTitle('Action').notSortable()
          .renderWith(actions1),*/
           
       ];  
     
   	
       function actions1(data, type, full) {
           vm.persons[full.roleId] = full;
                 
           return '<span class="form-group"><button  type="button" class="fa fa-pencil-square-o btn btn-dark-green btn-xs editItem popup_link popup_link"  autocomplete="off" title="Edit" ng-click="disController.edit(disController.persons['+full.roleId+'])"></button></span> <span class="form-group"><button class="fa fa-trash btn btn-danger btn-xs editItem popup_link popup_link" type="button" autocomplete="off" title="Delete" ng-click="disController.deleteRow(disController.persons['+full.roleId+'])"></button></span>' ;
           

       }
       
       //to generate serialno
       function serialNoHtml() {
	    	return serialNo++;
	    }
	
     

    
 
      //to edit data 
       vm.edit=function(role) {
    	   serialNo=1;
    	   $scope.data1={};
    	   $scope.data1.roleId = role.roleId;  
	       $scope.data1.roleName=role.roleName;
	       $scope.data1.roleDesc=role.roleDesc;
	     
	   }
    
       
       
       //to delete data
       vm.deleteRow=function(person){
    	 
      	 var id=person.roleId;
      	 swal({
             title: "Deleting Record",
             text: "Are you sure you want to delete this Role",
             type: "warning",
             showCancelButton: true,
             confirmButtonColor: "#007AFF",
             confirmButtonText: "Yes",
             closeOnConfirm: true
         }, function(isConfirm){
             if (isConfirm){
            	 $http({
              		 url: $rootScope.ctx +'/deleteRole/'+id,
              		 method: "DELETE",
              		 headers: {'Content-Type': 'application/json'}

              	 }).then(function onSuccess(response) {
              		 // setResponse("onSuccess",response);
              		 console.log("onSuccess",response);
              		 $("#saveMsg")
              		 .show()
              		 .html('<div class="alert alert-success"<strong>Role deleted successfully</strong> </div>')
              		 .fadeOut(5000);
              		 $scope.isDirty=false;
              		 vm.reloadData();
              		$scope.clear();
              		

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
    
    
     
       
       
	//to save data
	$scope.form = {
    		  submit: function (form) {
		
    			  if (form.$invalid) {
    		            var firstError = null;
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
		              
		ManageRoleService.saveRole($scope.data1,$scope.loginDetails).then(function(response){
			
        	console.log(response.data);
        		$("#saveMsg").show().html('<div class="alert alert-success"<strong>'+response.data.message+'</strong> </div>').fadeOut(5000);
        		$scope.clear();
            	vm.reloadData();
        		
		})
        	.catch(function(response){
            	$("#saveMsg").show().html('<div class="alert alert-danger"<strong>'+response.data.errorMessage+'</strong> </div>').fadeOut(5000);
        	});
		form.$setPristine(true); 
    	    }
	   }
	
   };
	
	$scope.clear=function(){
		 $scope.data1 ={};
		 
		 document.getElementById("checkboxid").checked = false;
	     $scope.value1=false;
		
			}
	

	
	
	
}]);