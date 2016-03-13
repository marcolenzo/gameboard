'use strict';

// Declare app level module which depends on filters, and services
angular.module(
		'myApp',
		[ 'ngResource', 
		  'ngRoute', 
		  'myApp.services', 
		  'myApp.overview', 
		  'myApp.createboard', 
		  'myApp.editboard', 
		  'myApp.gameresult',
		  'myApp.boarddetails', 
		  'myApp.gamehistory',
		  'myApp.gamedetails',
		  'myApp.boardstats',
		  'myApp.profile',
		  'myApp.actionlog',
		  'myApp.version',
		  'ui.bootstrap',
		  'ngTagsInput',
		  'ngFileUpload',
		  'chart.js' ]).

config([ '$routeProvider', function($routeProvider) {
	$routeProvider.otherwise({
		redirectTo : '/overview'
	});
} ]).

controller('MainCtrl', [ '$scope', '$rootScope', '$location', '$timeout', 'User', 'Board', 
                         function($scope, $rootScope, $location, $timeout, User, Board) {
			
	/**
	 * Start: Side navigation logic and resource preloading.
	 */
	$scope.showBoardMenu = false;
	$scope.board = undefined;
	$scope.user = undefined;
	$scope.isBoardAdmin = false;
	
	$scope.currentPath = "/overview";
	
	$scope.boardDetailsPaths = ['/boarddetails', '/gameresult', '/gamehistory', '/editboard', '/gamedetails', '/boardstats'];
	
	$scope.$on('$routeChangeSuccess', function (scope, next, current) {
		// Load user details if still undefined
		if($scope.user === undefined) {
			$scope.user = User.get({username: 'me'});
			$rootScope.user = $scope.user;
		}
		
		$timeout(function(){
			$rootScope.$broadcast('user-me', $scope.user)
		});
		
		if(next.$$route != null) {
			$scope.currentPath = next.$$route.originalPath;
			if($scope.boardDetailsPaths.includes($scope.currentPath)) {
                $scope.board = Board.get({id: next.params.boardId});
                $timeout(function(){
                    $rootScope.$broadcast('current-board', $scope.board);
                });

                $scope.board.$promise.then(function(board) {
                	if(board.admins.includes($scope.user.id)) {
                		$scope.isBoardAdmin = true;
                	}
                	else {
                		$scope.isBoardAdmin = false;
                	}
                });
				$scope.showBoardMenu = true;
			}
			else {
				$scope.showBoardMenu = false;
			}
		}
    });
	
	$scope.$on('user-updated', function(event, user) {
		$scope.user = user;
	});
	
	$scope.isMenuItemActive = function(path) {
		if(path === $scope.currentPath) {
			return 'active';
		}
		return '';
	}
	
	
	
}]);
