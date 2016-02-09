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

controller('MainCtrl', [ '$scope', '$rootScope', '$location', '$timeout', 'User', 'Gameboard', 
                         function($scope, $rootScope, $location, $timeout, User, Gameboard) {
			
	/**
	 * Start: Side navigation logic and resource preloading.
	 */
	$scope.showBoardMenu = false;
	$scope.board = undefined;
	$scope.user = undefined;
	
	$scope.currentPath = "/overview";
	
	$scope.boardDetailsPaths = ['/boarddetails', '/creategame']
	
	$scope.$on('$routeChangeSuccess', function (scope, next, current) {
		// Load user details if still undefined
		if($scope.user === undefined) {
			$scope.user = User.get({username: 'me'});
			$timeout(function(){
				$rootScope.$broadcast('user-me', $scope.user)
			});
		}
		
		
		if(next.$$route != null) {
			$scope.currentPath = next.$$route.originalPath;
			if($scope.boardDetailsPaths.includes($scope.currentPath)) {
				$scope.board = Gameboard.get({id: next.params.boardId});
				$timeout(function(){
					$rootScope.$broadcast('current-board', $scope.board);
				});
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
