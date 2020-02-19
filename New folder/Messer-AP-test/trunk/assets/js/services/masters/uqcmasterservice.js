(function() {
    'use strict'
    app.service('UqcMasterService', UqcMasterService);
    UqcMasterService.$inject = ['$http', '$rootScope', '$q'];

    function UqcMasterService($http, $rootScope, $q) {
        var obj = {
        		uqcMaster: uqcMaster,
        		excelSaveUqc:excelSaveUqc
         
        }

        function uqcMaster(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveuqc',
                data: data,
                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        
        function excelSaveUqc(data,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveExcelUqc',
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