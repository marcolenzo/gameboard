'use strict';

angular.module('myApp.boarddetails', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/boarddetails', {
		templateUrl : 'app/views/boarddetails/boarddetails.html',
		controller : 'BoardDetailsCtrl'
	});
} ])

.controller('BoardDetailsCtrl',	[ '$scope', '$location', 'User', 'Gameboard', 'Game', 
                               	  function($scope, $location, User, Gameboard, Game) {

	var params = $location.search();
	
	$scope.createGameHref = '#/creategame?boardId=' + params.id;
	
	$scope.user = undefined;
	$scope.board = undefined;
	$scope.showNoGamesInfo = false;
	$scope.games = Game.query({boardId: params.boardId});
	
	$scope.games.$promise.then(function(users) {
		if(users.length < 1) {
			$scope.showNoGamesInfo = true;
		}
		else {
			$scope.showNoGamesInfo = false;
		}
	});
	
	
	
	/**
	 * Events
	 */
	$scope.$on('user-me', function(event, data) {
		$scope.user = data;
	});
	
	$scope.$on('current-board', function(event, data) {
		$scope.board = data;
	});


}]);
