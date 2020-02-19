(function () {
    'use strict'
    app.service('CompanyService', CompanyService);
    CompanyService.$inject = ['$http', '$rootScope', '$q'];

    function CompanyService($http, $rootScope, $q) {
        var obj = {
            save: save
        }
        function save(data) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx + '/company/create',
                data: data,
            }).then(function (response) {
                deferred.resolve(response);
            }).catch(function (response) {
                deferred.reject(response);
            });
            return deferred.promise;
        }
        return obj;
    }
}());