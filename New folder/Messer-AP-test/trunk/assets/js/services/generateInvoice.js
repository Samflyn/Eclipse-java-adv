(function() {
    'use strict'
    app.service('GenerateInvoiceService', GenerateInvoiceService);
    GenerateInvoiceService.$inject = ['$http', '$rootScope', '$q','$stateParams'];

    function GenerateInvoiceService($http, $rootScope, $q,$stateParams) {
        var obj = {
            generateInvoice: generateInvoice,
            editInvoice:editInvoice,
            compareInvoices:compareInvoices,
            editCostInvoice:editCostInvoice,
            generateCostInvoice:generateCostInvoice
        }

        function generateInvoice(data) {
          console.log("data"+data)
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveInvoice',
                data: data,
                headers: {'Content-Type': 'application/json',
                'Authorization': $http.defaults.headers.common.Authorization
                }
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }

      function generateCostInvoice(data) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx +'/saveInvoice',
                data: data,
                headers: {'Content-Type': 'application/json',
                'Authorization': $http.defaults.headers.common.Authorization
              }
                }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }

        function editInvoice(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/getInvoice/'+$stateParams.headerId,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(data)}
           
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        
        function editCostInvoice(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/bulkupload/getInvoice/'+$stateParams.headerId,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(data)}
           
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }

        function compareInvoices(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',

            url: $rootScope.ctx +'/generatePdf',
            params: {
            	filepath:data,

            },
            responseType : 'arraybuffer',
            headers: {'Content-Type': 'application/json'}
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        	/*return $http.get('api/downloadPDF', { responseType: 'arraybuffer' }).then(function (response) {
                return response;
            });*/
        }

        return obj;
    }
    // send to erp service
    app.service('SendToErpService', SendToErpService);
    SendToErpService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function SendToErpService($http, $rootScope, $q, $stateParams) {
        var obj = {
            sendToErpObj: sendToErpObj,
            sendForApproval:sendForApproval,
            sendCostToErpObj:sendCostToErpObj,
            sendForReject:sendForReject
            }

        function sendToErpObj(data,loginDetails) {
            var deferred = $q.defer();
            $http({
            method: 'POST',
            url: $rootScope.ctx +'/sendToErp',
            data: data,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        

	    function sendCostToErpObj(data,loginDetails) {
        var deferred = $q.defer();
        $http({
        method: 'POST',
        url: $rootScope.ctx +'/sendCostInvoiceToERP',
        data: data,
        headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
        }).then(function(response) {
           deferred.resolve(response);
        }).catch(function(response){
          deferred.reject(response);
        });
        return deferred.promise;
    }

        function sendForApproval(data,loginDetails) {
            var deferred = $q.defer();
            $http({
            method: 'POST',
            url: $rootScope.ctx +'/sendForApproval',
            data: data,
            headers: {'Content-Type': 'application/json','loginDetails':JSON.stringify(loginDetails)}
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }

        function sendForReject(headerId,rejectReason,invoiceType) {
            var deferred = $q.defer();
            $http({
            method: 'POST',
            url: $rootScope.ctx +'/sendForReject',
            params:{"headerId":headerId,"rejectReason":rejectReason,"invoiceType":invoiceType},
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }

        return obj;
    }

    app.service('refreshToErpService', refreshToErpService);
    refreshToErpService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function refreshToErpService($http, $rootScope, $q, $stateParams) {
        var obj = {
            refreshToErpObj: refreshToErpObj
        }

        function refreshToErpObj(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/getErpInvByInvId/'+$stateParams.headerId,
            headers: {'Content-Type': 'application/json'}
            }).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        return obj;
    }
    
    app.service('SendToCustomer', SendToCustomer);
    SendToCustomer.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function SendToCustomer($http, $rootScope, $q, $stateParams) {
        var obj = {
        		sendToCustomerObj: sendToCustomerObj
        }

        function sendToCustomerObj(data,loginDetails) {
        	  var deferred = $q.defer();
              $http({
                  method: 'POST',
                  url: $rootScope.ctx +'/sendToCustomer',
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