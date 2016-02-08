'use strict';

// Declare app level module which depends on filters, and services
angular.module(
		'myApp',
		[ 'ngResource', 
		  'ngRoute', 
		  'myApp.services', 
		  'myApp.overview', 
		  'myApp.createboard', 
		  'myApp.creategame', 
		  'myApp.boarddetails', 
		  'myApp.version',
		  'ui.bootstrap', 
		  'ngTagsInput' ]).

config([ '$routeProvider', function($routeProvider) {
	$routeProvider.otherwise({
		redirectTo : '/overview'
	});
} ]).

controller('MainCtrl', [ '$scope', '$location', 'User', function($scope, $location, User) {
			
	/**
	 * Start: Side navigation logic.
	 */
	$scope.currentPath = "/overview";
	
	$scope.$on('$routeChangeSuccess', function (scope, next, current) {
		if(next.$$route != null) {
			$scope.currentPath = next.$$route.originalPath;
		}
    });
	
	$scope.isMenuItemActive = function(path) {
		if(path === $scope.currentPath) {
			return 'active';
		}
		return '';
	}
	
}]);
