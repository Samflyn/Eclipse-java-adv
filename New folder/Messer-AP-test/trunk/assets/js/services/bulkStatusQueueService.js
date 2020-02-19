(function() {
    'use strict'
    app.service('viewBulkStatusQueueService', viewBulkStatusQueueService);
    viewBulkStatusQueueService.$inject = ['$http', '$rootScope', '$q'];

    function viewBulkStatusQueueService($http, $rootScope, $q) {
        var obj = {
            bulkUploadStatusQueue: bulkUploadStatusQueue
        }

        function bulkUploadStatusQueue(data) {
            var deferred = $q.defer();
            $http.get($rootScope.ctx+"/bulkupload/getBulkUploadStatus/"+data.userid+"/"+data.status)
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