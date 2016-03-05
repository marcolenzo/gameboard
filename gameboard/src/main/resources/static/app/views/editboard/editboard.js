'use strict';

angular.module('myApp.editboard', [ 'ngRoute' ])

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
			
			$scope.currentPlayersNicks = new Array();
			$scope.users = undefined;
			$scope.selectedPlayer = "";
			$scope.selectedPlayerAdmin = false;
			$scope.selectedPlayerOwner = false;
			$scope.nicknames = new Array();
			$scope.isNotOwner = true;
			
			$scope.board = Board.get({id: params.boardId});
			$scope.board.$promise.then(function (board) {
				
				if(board.owners.includes($scope.user.id)) {
					$scope.isNotOwner = false;
				}
				
				 jQuery.each(board.players, function(index, value) {
					 $scope.currentPlayersNicks.push(value.nickname);
					 
					 if($scope.board.admins.includes(value.userId)) {
						 value.isAdmin = true;
					 }
					 if($scope.board.owners.includes(value.userId)) {
						 value.isOwner = true;
					 }
					 
				 });

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
			
			$scope.addPlayer =  function() {
				if($scope.selectedPlayer !== "") {
					var player = {};
					var user = nickMap[$scope.selectedPlayer];
					
					player.userId = user.id;
					player.nickname = user.nickname;
					player.elo = 1500;
					player.matchesPlayed = 0;
					player.matchesWon = 0;
					player.matchesPlayedAsResistance = 0;
					player.matchesWonAsResistance = 0;
					player.matchesPlayedAsSpy = 0;
					player.matchesWonAsSpy = 0;
					
					if($scope.selectedPlayerAdmin) {
						player.isAdmin = true;
					}
					if($scope.selectedPlayerOwner) {
						player.isOwner = true;
					}
					
					$scope.board.players.push(player);
				}
			}

			$scope.updateBoard = function() {
				$scope.board.admins = new Array();
				$scope.board.owners = new Array();
				jQuery.each($scope.board.players, function(index, value) {
					if(value.isAdmin) {
						$scope.board.admins.push(value.userId);
					}
					if(value.isOwner) {
						$scope.board.owners.push(value.userId);
					}
				});
				
				Board.update({ id: $scope.board.id }, $scope.board, function() {
					alert("Success");
					$location.path('/boarddetails');
				}, function() {
					alert("Fail");
				});
			}
			
			

		} ]);
