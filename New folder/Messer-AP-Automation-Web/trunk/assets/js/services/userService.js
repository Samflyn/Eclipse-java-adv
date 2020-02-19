(function () {
    'use strict'
    app.service('UserService', UserService);
    UserService.$inject = ['$http', '$rootScope', '$q'];

    function UserService($http, $rootScope, $q) {
        var obj = {
            saveUser: saveUser
        }
        function saveUser(data) {
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