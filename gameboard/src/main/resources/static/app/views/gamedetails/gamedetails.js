'use strict';

angular.module('myApp.gamedetails', [ 'ngRoute' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/gamedetails', {
		templateUrl : 'app/views/gamedetails/gamedetails.html',
		controller : 'GameDetailsCtrl'
	});
} ])

.controller('GameDetailsCtrl',	[ '$scope', '$location', 'Game', '$rootScope', function($scope, $location, Game, $rootScope) {

	var n = 1;
	var params = $location.search();
	var currentDate = new Date();
	var voteUntilDate = undefined;
	
	$scope.game = Game.get({id: params.gameId});
	$scope.allowVoting = false;
	
	$rootScope.user.$promise.then(function(user){
		$scope.user = user;
		$scope.board.$promise.then(function(board) {
			if(board.admins.includes($scope.user.id)) {
				$scope.isAdmin = true;
			}
		});
	});
	
	$scope.isMvpEligible = function(index) {
		if($scope.allowVoting) {
			var checked = $scope.game.players[index];
			if(checked === $scope.user.id) {
				return false;
			}
			else if($scope.game.resistanceWin && !$scope.game.spies.includes(checked)) {
				return true;
			}
			else if(!$scope.game.resistanceWin && $scope.game.spies.includes(checked)) {
				return true;
			}
		}
		return false;
	}
	
	$scope.isMvp = function(index) {
		var checked = $scope.game.players[index];
		if(checked === $scope.game.mvp) {
			return true;
		}
		return false;
	}
	
	$scope.vote = function(index) {
		Game.vote({id: params.gameId}, $scope.game.players[index], function(data){
			alert('Your vote was casted.');
			$scope.allowVoting = false;
		}, function(response){
			alert('Fail! ' + response.data.error);
		});
	}
	
	$scope.game.$promise.then(function(){
		
		$scope.game.time = $scope.game.startTime[2] + '/' + $scope.game.startTime[1] + '/' + 
			$scope.game.startTime[0] + ' ' + $scope.game.startTime[3] + 
			($scope.game.startTime[4] < 10 ? ':0' + $scope.game.startTime[4] : ':' + $scope.game.startTime[4]);
		
		var playersMap = {};
		$scope.game.playersDetails = new Array();
		
		jQuery.each($scope.game.playerStats, function(index, player) {
			playersMap[player.userId] = player;
		});
		
		jQuery.each($scope.game.players, function(index, userId) {
			var playerStats = playersMap[userId];
			
			if($scope.game.spies.includes(userId)) {
				playerStats.team = 'SPIES';
			}
			else {
				playerStats.team = 'RESISTANCE';
			}
			
			$scope.game.playersDetails.push(playerStats);
		});
		
		$scope.game.winner = $scope.game.resistanceWin ? 'RESISTANCE' : 'SPIES';
		
		// MVP Election
		if($scope.game.voteUntil != null) {
			var currentPlayerVoted = false;
			jQuery.each($scope.game.votes, function(index, value) {
				if($scope.user.id === index) {
					currentPlayerVoted = true;
				}
			});
			
			voteUntilDate = new Date($scope.game.voteUntil[0], $scope.game.voteUntil[1], $scope.game.voteUntil[2], $scope.game.voteUntil[3], $scope.game.voteUntil[4], $scope.game.voteUntil[6], 0);
			if(voteUntilDate > currentDate && !currentPlayerVoted) {
				$scope.allowVoting = true;
				$scope.election = new Array();
				if($scope.game.resistanceWin) {
					jQuery.each($scope.game.players, function(index, value){
						if(!$scope.game.spies.includes(value)) {
							$scope.election.push(value);
						}
					});
				}
				else {
					$scope.election = $scope.game.spies;
				}
			}
		}
		
	});
	
	
	
}]);
