'use strict';

angular.module('myApp.boardstats', [ 'ngRoute' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/boardstats', {
		templateUrl : 'app/views/boardstats/boardstats.html',
		controller : 'BoardStatsCtrl'
	});
} ])

.controller('BoardStatsCtrl',	[ '$scope', '$location', 'Board', function($scope, $location, Board) {

	var params = $location.search();
	
	$scope.labels = ["Spies", "Resistance"];
	$scope.colours = ["#F7464A", "#6050DC"];
	
	// Global
	$scope.global = undefined;
	$scope.r3v2 = undefined;
	$scope.r4v2 = undefined;
	$scope.r4v3 = undefined;
	$scope.r5v3 = undefined;
	$scope.r6v3 = undefined;
	$scope.r6v4 = undefined;
	
	
	$scope.board = Board.get({id: params.boardId});
	
	$scope.board.$promise.then(function() {
		var b = $scope.board.boardStatistics;
		$scope.global = [(b.matchesPlayed - b.matchesWonByResistance), b.matchesWonByResistance];
		$scope.r3v2 = [(b.matchesPlayed3v2 - b.matchesWonByResistance3v2), b.matchesWonByResistance3v2];
		$scope.r4v2 = [(b.matchesPlayed4v2 - b.matchesWonByResistance4v2), b.matchesWonByResistance4v2];
		$scope.r4v3 = [(b.matchesPlayed4v3 - b.matchesWonByResistance4v3), b.matchesWonByResistance4v3];
		$scope.r5v3 = [(b.matchesPlayed5v3 - b.matchesWonByResistance5v3), b.matchesWonByResistance5v3];
		$scope.r6v3 = [(b.matchesPlayed6v3 - b.matchesWonByResistance6v3), b.matchesWonByResistance6v3];
		$scope.r6v4 = [(b.matchesPlayed6v4 - b.matchesWonByResistance6v4), b.matchesWonByResistance6v4];
	});
	
}]);
