'use strict';

// Declare app level module which depends on filters, and services
angular.module(
		'myApp',
		[ 'ngResource', 'ngRoute', 'myApp.services', 'myApp.home',
				'myApp.dashboard', 'myApp.createboard', 'myApp.creategame', 'myApp.boarddetails', 'myApp.version',
				'ui.bootstrap', 'ngTagsInput' ]).

config([ '$routeProvider', function($routeProvider) {
	$routeProvider.otherwise({
		redirectTo : '/dashboard'
	});
} ]).

controller('MainCtrl', [ '$scope', '$location', 'User', function($scope, $location, User) {
			$scope.getClass = function(path) {
				if ($location.path().substr(0, path.length) === path) {
					return 'active';
				} else {
					return '';
				}
			}
			
			$scope.showBoardMenu = function() {
				var pages = ["/boarddetails"]
				if(pages.includes($location.path())){
					return true;
				}
				return false;
			}
		} ]);
