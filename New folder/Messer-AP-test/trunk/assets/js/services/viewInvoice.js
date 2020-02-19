(function() {
    'use strict'
    app.service('ViewInvoiceService', ViewInvoiceService);
    ViewInvoiceService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function ViewInvoiceService($http, $rootScope, $q, $stateParams) {
        var obj = {
            viewInvoice: viewInvoice
        }

        function viewInvoice(data,tenantobj) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/getInvoice/'+$stateParams.headerId,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(tenantobj)}
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        return obj;
    }
    
    app.service('ViewCostInvoiceService', ViewCostInvoiceService);
    ViewCostInvoiceService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function ViewCostInvoiceService($http, $rootScope, $q, $stateParams) {
        var obj = {
            viewCostInvoice: viewCostInvoice
        }

        function viewCostInvoice(data,tenantobj) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/bulkupload/getInvoice/'+$stateParams.headerId,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(tenantobj)}
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