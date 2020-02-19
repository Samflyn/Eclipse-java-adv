(function() {
    'use strict'
    app.service('costInvoicReportsService', costInvoicReportsService);
    costInvoicReportsService.$inject = ['$http', '$rootScope', '$q'];

    function costInvoicReportsService($http, $rootScope, $q) {
        var obj = {
        		getInvoceReport: getInvoceReport,
        		exportInvoceReport:exportInvoceReport,
        		exportBulkInvoceReport:exportBulkInvoceReport
        }
        function getInvoceReport(fromdate,todate,loginDetails,financialPeriod,invNo,status) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: $rootScope.ctx +'/getCustCostInvoiceReport',
               params:{"fromdate":fromdate,"todate":todate,"genFinancialPeriod":financialPeriod,"invNo":invNo,"status":status},
                headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
                	 console.log(response.data)
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        function exportInvoceReport(fromdate,todate,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: $rootScope.ctx +'/exportCustCostInvoiceReport',
                responseType: 'arraybuffer',
               params:{"fromdate":fromdate,"todate":todate},
                headers: {'Content-Type': 'application/json', 'loginDetails':JSON.stringify(loginDetails)}
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        function exportBulkInvoceReport(fromdate,todate,loginDetails) {
            var deferred = $q.defer();
            $http({
                method: 'GET',
                url: $rootScope.ctx +'/exportBulkInvoiceReport',
                responseType: 'arraybuffer',
               params:{"fromdate":fromdate,"todate":todate},
                headers: {'Content-Type': 'application/json', 'loginDetails':JSON.stringify(loginDetails)}
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