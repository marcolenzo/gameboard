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

controller('MainCtrl', [ '$scope', '$location', 'User', 'Gameboard', function($scope, $location, User, Gameboard) {
			
	/**
	 * Start: Side navigation logic.
	 */
	$scope.showBoardMenu = false;
	$scope.board = undefined;
	$scope.currentPath = "/overview";
	
	$scope.boardDetailsPaths = ['/boarddetails']
	
	$scope.$on('$routeChangeSuccess', function (scope, next, current) {
		if(next.$$route != null) {
			$scope.currentPath = next.$$route.originalPath;
			if($scope.boardDetailsPaths.includes($scope.currentPath)) {
				$scope.board = Gameboard.get({id: next.params.boardId});
				$scope.showBoardMenu = true;
			}
			else {
				$scope.showBoardMenu = false;
			}
		}
    });
	
	$scope.isMenuItemActive = function(path) {
		if(path === $scope.currentPath) {
			return 'active';
		}
		return '';
	}
	
	
	
}]);
