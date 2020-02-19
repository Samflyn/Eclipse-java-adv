(function() {
    'use strict'
    app.service('ItemMappingService', ItemMappingService);
    ItemMappingService.$inject = ['$http', '$rootScope', '$q'];

    function ItemMappingService($http, $rootScope, $q) {
        var obj = {
        		itemMapping: itemMapping,
        		saveExcelItemMapping:saveExcelItemMapping
         
        }

        function itemMapping(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveItemMapping',
                data: data,
                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        function saveExcelItemMapping(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveExcelItemMapping',
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