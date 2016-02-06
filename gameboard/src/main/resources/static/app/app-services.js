'use strict';

angular.module('myApp.services', ['ngRoute'])

.factory('AuthService', ['$http', '$window', '$rootScope', 'AUTH_EVENTS', function ($http, $window, $rootScope, AUTH_EVENTS) {
  
	var authService = {};
	
	authService.login = function(username, password) {
	    $http.get('/classified/oauth/token?grant_type=password&username=' + username + "&password=" + password,
	    		{ headers: {'Authorization': 'Basic dW50cnVzdGVkOnNlY3JldA=='}})
	      .success(function (data, status, headers, config) {
	        sessionStorage.access_token = data.access_token;
	        sessionStorage.refresh_token = data.refresh_token;
	        sessionStorage.username = username;
	        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
	        console.log(username + ' logged in.');
	      })
	      .error(function (data, status, headers, config) {
	        // Erase the token if the user fails to log in
	        delete sessionStorage.access_token;
	        delete sessionStorage.refresh_token;
	        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
	        alert('Error: Invalid user or password');
	      });
	};
	
	authService.isAuthenticated = function () {
		if($window.sessionStorage.access_token) {
			return true;
		}
		else {
			return false;
		}
	};
 
	return authService;
}])

.service('User', [ '$resource', function(resource) {
	return resource('/api/user/:username/');
	// Note resource ignores trailing slash... need to switch to $http
}]);
