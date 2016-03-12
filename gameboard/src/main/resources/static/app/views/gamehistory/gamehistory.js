'use strict';

angular.module('myApp.gamehistory', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/gamehistory', {
		templateUrl : 'app/views/gamehistory/gamehistory.html',
		controller : 'GameHistoryCtrl'
	});
} ])

.controller('GameHistoryCtrl',	[ '$scope', '$location', '$rootScope', '$route', 'Board', 'Game', function($scope, $location, $rootScope, $route, Board, Game) {

	var params = $location.search();
	
	$scope.isAdmin = false;
	$scope.board = Board.get({id: params.boardId});
	$rootScope.user.$promise.then(function(user){
		$scope.user = user;
		$scope.board.$promise.then(function(board) {
			if(board.admins.includes($scope.user.id)) {
				$scope.isAdmin = true;
			}
		});
	});
	
	
	$scope.games = Game.query({boardId: params.boardId});
	$scope.games.$promise.then(function(games) {
		jQuery.each($scope.games, function(index, value) {
			value.time = value.startTime[2] + '/' + value.startTime[1] + '/' + value.startTime[0] + ' ' + value.startTime[3] + (value.startTime[4] < 10 ? ':0' + value.startTime[4] : ':' + value.startTime[4]);
			
			var playersMap = {};
			value.spiesList = '';
			value.resistanceList = '';
			
			jQuery.each(value.playerStats, function(index, player) {
				playersMap[player.userId] = player;
			});
			
			jQuery.each(value.players, function(index, userId) {
				if(value.spies.includes(userId)) {
					value.spiesList += playersMap[userId].nickname + ' ';
				}
				else {
					value.resistanceList += playersMap[userId].nickname + ' ';
				}
			});
			
			value.spiesList.trim();
			value.resistanceList.trim();
			value.winner = value.resistanceWin ? 'RESISTANCE' : 'SPIES';
			
			
		});
	});
	
	$scope.deleteGame = function(index) {
		var game = $scope.games[index];
		Game.delete({id: game.id}, function() {
			$scope.games.splice(index, 1);
			alert('Success!');
			$route.reload();
		}, function() {
			alert('Failed!');
			$route.reload();
		});
	}
	
}]);
