'use strict';

angular.module('myApp.gamehistory', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/gamehistory', {
		templateUrl : 'app/views/gamehistory/gamehistory.html',
		controller : 'GameHistoryCtrl'
	});
} ])

.controller('GameHistoryCtrl',	[ '$scope', '$location', 'Board', 'Game', function($scope, $location, Board, Game) {

	var params = $location.search();
	
	$scope.games = Game.query({boardId: params.boardId});
	$scope.games.$promise.then(function(games) {
		jQuery.each($scope.games, function(index, value) {
			value.time = value.startTime[2] + '/' + value.startTime[1] + '/' + value.startTime[0] + ' ' + value.startTime[3] + (value.startTime[4] < 10 ? ':0' + value.startTime[4] : ':' + value.startTime[4]);
		});
	});
	
}]);
