(function() {
    'use strict'
    app.service('ItemMasterService', ItemMasterService);
    ItemMasterService.$inject = ['$http', '$rootScope', '$q'];

    function ItemMasterService($http, $rootScope, $q) {
        var obj = {
        		saveItemMaster: saveItemMaster,
        		excelItemMaster:excelItemMaster
        }
        function saveItemMaster(data,loginDetails) {
        	
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveItemMaster',
                data: data,
                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        
        function excelItemMaster(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveExcelItems',
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