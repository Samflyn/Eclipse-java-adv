(function() {
    'use strict'
    app.service('viewCustomerPaymentService', viewCustomerPaymentService);
    viewCustomerPaymentService.$inject = ['$http', '$rootScope', '$q'];

    function viewCustomerPaymentService($http, $rootScope, $q) {
        var obj = {
           
        		invoiceQueue:invoiceQueue,
        		invoiceQueueForExcel:invoiceQueueForExcel
        }

      
        function invoiceQueue(data) {
            var deferred = $q.defer();
     
        
             $http({
            method: 'GET',
            url: $rootScope.ctx+'/getPaymentDue',
                    params: {
            	tenantId:data.tenantId,
            	workFlowStatus:data.status,
            	fromDate:data.fromDate,
            	toDate:data.toDate,
            	
            }
          
        })
            .then(function(result) {
               deferred.resolve(result);
            }).catch(function(result){
              deferred.reject(result);
            });
            return deferred.promise;
        }
        
        function invoiceQueueForExcel(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            responseType: 'arraybuffer',
            url: $rootScope.ctx+'/paymentExportToExcel',
                      params: {
            	tenantId:data.tenantId,
                workFlowStatus:data.status,
            	fromDate:data.fromDate,
            	toDate:data.toDate,
            	loginDetails:data.loginDetails,
            }
          
        })
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