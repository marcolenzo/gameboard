'use strict';

angular.module('myApp.editboard', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/editboard', {
		templateUrl : 'app/views/editboard/editboard.html',
		controller : 'EditBoardCtrl'
	});
} ])

.controller('EditBoardCtrl',
		[ '$scope', '$location', 'User', 'Board', function($scope, $location, User, Board) {

			var params = $location.search();
			var nickMap = {};
			
			$scope.currentPlayers = '';
			$scope.currentPlayersNicks = new Array();
			$scope.tags = undefined;
			$scope.users = undefined;
			$scope.nicknames = new Array();
			
			$scope.board = Board.get({id: params.boardId});
			$scope.board.$promise.then(function (board) {
				 jQuery.each(board.players, function(index, value) {
					 $scope.currentPlayersNicks.push(value.nickname);
					 $scope.currentPlayers += value.nickname;
					 if(index < $scope.board.players.length - 1) {
						 $scope.currentPlayers += ', ';
					 }
					 
					 $scope.users = User.query();
					 
					 $scope.users.$promise.then(function(users) {
							angular.forEach($scope.users, function(value, key) {
								if(!$scope.currentPlayersNicks.includes(value.nickname) && !$scope.nicknames.includes(value.nickname)) {
									this.push(value.nickname);
									nickMap[value.nickname] = value;
								}
							}, $scope.nicknames);
						});
				 });
				 
			});
			
			
			
			

			$scope.getMatchingStates = function(query) {
				return jQuery.grep($scope.nicknames, function(n, i) {
					if (n.indexOf(query) > -1) {
						return true;
					}
					return false;
				});
			}

			$scope.updateBoard = function() {
				angular.forEach($scope.tags, function(value, key) {
					var player = {};
					var user = nickMap[value.text];
					
					player.userId = user.id;
					player.nickname = user.nickname;
					player.elo = 1500;
					player.matchesPlayed = 0;
					player.matchesWon = 0;
					player.matchesPlayedAsResistance = 0;
					player.matchesWonAsResistance = 0;
					player.matchesPlayedAsSpy = 0;
					player.matchesWonAsSpy = 0;
					
					this.push(player);
				}, $scope.board.players);
				 
				Board.update({ id: $scope.board.id }, $scope.board, function() {
					alert("Success");
					$location.path('/dashboard');
				}, function() {
					alert("Fail");
				});
			}

		} ]);
