(function() {
    'use strict'
    app.service('UqcMappingService', UqcMappingService);
    UqcMappingService.$inject = ['$http', '$rootScope', '$q'];

    function UqcMappingService($http, $rootScope, $q) {
        var obj = {
        		uqcMapping: uqcMapping,
        		saveExcelUqcMapping:saveExcelUqcMapping
        }

        function uqcMapping(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveUqcMapping',
                data: data,
                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        
        function saveExcelUqcMapping(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveExcelUqcMapping',
                data: data,
                headers: {'Content-Type':'application/json','loginDetails':JSON.stringify(loginDetails)}
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