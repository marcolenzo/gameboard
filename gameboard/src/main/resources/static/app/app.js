'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngResource',                       
  'ngRoute',
  'myApp.services',
  'myApp.home',
  'myApp.version',
  'ui.bootstrap'
]).

config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]).

config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
}]).

factory('authInterceptor', ['$rootScope', '$q',	'$window', function authInterceptorFactory($rootScope, $q, $window) {
	return {
		request : function(config) {
			config.headers = config.headers || {};
			if ($window.sessionStorage.access_token) {
				config.headers.Authorization = 'Bearer ' + $window.sessionStorage.access_token;
			}
//			config.headers.Accept = "application/json";
			return config;
		},
		response : function(response) {
			if (response.status === 401) {
				// handle the case where the user is not
				// authenticated
			}
			return response || $q.when(response);
		}
	};
}]).

constant('AUTH_EVENTS', {
	  loginSuccess: 'auth-login-success',
	  loginFailed: 'auth-login-failed',
	  logoutSuccess: 'auth-logout-success',
	  sessionTimeout: 'auth-session-timeout',
	  notAuthenticated: 'auth-not-authenticated',
	  notAuthorized: 'auth-not-authorized'
}).

controller('MainCtrl', ['$scope', 'AUTH_EVENTS', '$location', 'User', function($scope, AUTH_EVENTS, $location, User) {
	
	$scope.currentPage = null;
	$scope.previousPage = null;
	
	$scope.user = null;
	$scope.loggedIn = false;
	
	$scope.$on('$routeChangeSuccess', function (scope, next, current) {
		if(next && next.$$route && next.$$route.originalPath) {
			$scope.currentPage = next.$$route.originalPath.split('/')[1];
		}
		
		if(current && current.$$route && current.$$route.originalPath) {
			$scope.previousPage = current.$$route.originalPath.split('/')[1];
		}		
        
    });
	
	
	$scope.$on(AUTH_EVENTS.loginSuccess, function() {
		console.log('loginSuccess event received.');
		console.log(sessionStorage.username + ' logged in.');
		
		 $scope.user = User.get({username: escape(sessionStorage.username)});
		 $scope.loggedIn = true;
		
		 if($scope.previousPage) {
	     	$location.path('/' + $scope.previousPage);
	     }
		 else {
			 $location.path('/home');
		 }
	});
	
	$scope.$on(AUTH_EVENTS.loginFailed, function() {
		console.log('loginfailed event received.');
		$scope.user = null;
		$scope.loggedIn = false;
	});
	
}]);


