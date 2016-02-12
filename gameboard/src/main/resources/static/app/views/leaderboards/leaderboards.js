'use strict';

angular.module('myApp.leaderboards', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/leaderboards', {
		templateUrl : 'app/views/leaderboards/leaderboards.html',
		controller : 'LeaderboardsCtrl'
	});
} ])

.controller('LeaderboardsCtrl',
		[ '$scope', '$location', 'User', 'Board', function($scope, $location, User, Board) {


		} ]);
