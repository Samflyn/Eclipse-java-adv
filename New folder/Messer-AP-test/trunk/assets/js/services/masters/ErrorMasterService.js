(function() {
    'use strict'
    app.service('ManageErrorMasterService', ManageErrorMasterService);
    ManageErrorMasterService.$inject = ['$http', '$rootScope', '$q'];

    function ManageErrorMasterService($http, $rootScope, $q) {
        var obj = {
        		saveErrorMaster: saveErrorMaster
        }
        function saveErrorMaster(data,loginDetails) {
        	
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveErrorMaster',
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