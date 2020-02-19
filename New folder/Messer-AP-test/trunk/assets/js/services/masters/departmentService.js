(function () {
    'use strict'
    app.service('DepartmentService', DepartmentService);
    DepartmentService.$inject = ['$http', '$rootScope', '$q'];

    function DepartmentService($http, $rootScope, $q) {
        var obj = {
            saveDepartment: saveDepartment
        }
        function saveDepartment(data) {
            var deferred = $q.defer();
            $http({
                method: 'POST',
                url: $rootScope.ctx + '/user/create',
                data: data,
                headers: { 'Content-Type': 'application/json' }
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