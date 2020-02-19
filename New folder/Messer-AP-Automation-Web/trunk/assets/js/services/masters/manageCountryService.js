(function () {
	'use strict'
	app.service('ManageCountryService', ManageCountryService);
	ManageCountryService.$inject = ['$http', '$rootScope', '$q'];


	function ManageCountryService($http, $rootScope, $q) {
		var obj = {
			saveCountry: saveCountry
		}

		//to save country
		function saveCountry(data, loginDetails) {

			var deferred = $q.defer();
			$http({
				method: 'POST',
				url: $rootScope.ctx + '/saveCountry',
				data: data,
				headers: { 'Content-Type': 'application/json', 'loginDetails': JSON.stringify(loginDetails) }
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