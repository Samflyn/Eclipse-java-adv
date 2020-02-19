(function() {
	
	 app.service('ManageRoleService',ManageRoleService);
	 ManageRoleService.$inject = ['$http', '$rootScope', '$q'];
	

	 function ManageRoleService($http, $rootScope, $q) {
	        var obj = {
	        		saveRole: saveRole
	        }
	        
	   	 //to save role
	        function saveRole(data,loginDetails) {
	        
	            var deferred = $q.defer();
	            $http({
	                method: 'POST',
	                url: $rootScope.ctx +'/saveUserRoles',
	                data: data,
	                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
	                }).then(function(response) {
	               deferred.resolve(response);
	            }).catch(function(response){
	              deferred.reject(response);
	            });
	            return deferred.promise;
	        }
	        return obj;
	    }
	
}());