(function() {
    'use strict'
    app.service('processedDataQueueService', processedDataQueueService);
    processedDataQueueService.$inject = ['$http', '$rootScope', '$q'];

    function processedDataQueueService($http, $rootScope, $q) {
        var obj = {
            processedDataQueue: processedDataQueue
        }

        function processedDataQueue(data) {
            var deferred = $q.defer();
            $http.get($rootScope.ctx+'/getAllInvoice/'+data.userid+"/"+data.status)
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