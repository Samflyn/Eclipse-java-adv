(function() {
	'use strict'
	 app.service('ManageStateService', ManageStateService);
	 ManageStateService.$inject = ['$http', '$rootScope', '$q'];
	

	 function ManageStateService($http, $rootScope, $q) {
	        var obj = {
	        		saveState: saveState
	        }
	        
	   	 //to save country
	        function saveState(data,loginDetails) {
	        	
	            var deferred = $q.defer();
	            $http({
	                method: 'POST',
	                url: $rootScope.ctx +'/savestate',
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