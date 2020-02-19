(function() {
    'use strict'
    app.service('EditInvoiceService', EditInvoiceService);
    EditInvoiceService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function EditInvoiceService($http, $rootScope, $q, $stateParams) {
        var obj = {
            editInvoice: editInvoice
        }

        function editInvoice(data) {
            var deferred = $q.defer();
            $http({
            method: 'GET',
            url: $rootScope.ctx +'/getInvoice/'+$stateParams.headerId,
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
    //send to erp service
    app.service('SendToErpService', SendToErpService);
    SendToErpService.$inject = ['$http', '$rootScope', '$q', '$stateParams'];

    function SendToErpService($http, $rootScope, $q, $stateParams) {
        var obj = {
            sendToErpObj: sendToErpObj
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
        return obj;
    }

    //refresg to erp service
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


}());