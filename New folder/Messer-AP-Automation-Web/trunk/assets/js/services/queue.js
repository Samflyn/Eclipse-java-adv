(function() {
    'use strict'
    app.service('InvoiceQueueService', InvoiceQueueService);
    InvoiceQueueService.$inject = ['$http', '$rootScope', '$q'];

    function InvoiceQueueService($http, $rootScope, $q) {
        var obj = {
            invoiceQueue: invoiceQueue
        }

        function invoiceQueue(data) {
            var deferred = $q.defer();
            $http.get($rootScope.ctx+'/getAllInvoices/'+data.tenantId+'/'+data.status+'/'+data.userId)
            .then(function(result) {
               deferred.resolve(result);
            }).catch(function(result){
              deferred.reject(result);
            });
            return deferred.promise;
        }
        return obj;
    }
    
    //saved invoice Queue
    app.service('savedQueueService', savedQueueService);
    savedQueueService.$inject = ['$http', '$rootScope', '$q'];

    function savedQueueService($http, $rootScope, $q) {
        var obj = {
            savedQueue: savedQueue
        }

        function savedQueue(data) {
            var deferred = $q.defer();
            $http.get($rootScope.ctx+'/getAllInvoices/'+data.tenantId+'/'+data.status+'/'+data.userId)
            .then(function(result) {
               deferred.resolve(result);
            }).catch(function(result){
              deferred.reject(result);
            });
            return deferred.promise;
        }
        return obj;
    }


}());