(function() {
    'use strict'
    app.service('PermissionMasterService', PermissionMasterService);
    PermissionMasterService.$inject = ['$http', '$rootScope', '$q'];

    function PermissionMasterService($http, $rootScope, $q) {
        var obj = {
        		savePermissionMaster: savePermissionMaster
        }
        function savePermissionMaster(data,loginDetails) {
        	
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/savePermission',
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