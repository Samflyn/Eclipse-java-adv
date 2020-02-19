(function() {
    'use strict'
    app.service('LoginService', LoginService);
    LoginService.$inject = ['$http', '$rootScope', '$q'];

    function LoginService($http, $rootScope, $q) {
        var obj = {
            login: login
        }

        function login(data) {
            var deferred = $q.defer();
            $http({method : 'POST', url : $rootScope.ctx+'/api/auth/signin', data : data}).then(function(response) {
               deferred.resolve(response);
            }).catch(function(response){
              deferred.reject(response);
            });
            return deferred.promise;
        }
        return obj;
    }
}());