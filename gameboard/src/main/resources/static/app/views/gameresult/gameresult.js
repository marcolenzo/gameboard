'use strict';

angular.module('myApp.gameresult', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/gameresult', {
		templateUrl : 'app/views/gameresult/gameresult.html',
		controller : 'GameResultCtrl'
	});
} ])

.controller('GameResultCtrl',
		[ '$scope', '$location', 'Board', 'User', 'Game', function($scope, $location, Board, User, Game) {
			
			var params = $location.search();
			var nickMap = {}; 
			
			$scope.selectedPlayer = "";
			$scope.selectedPlayerSpy = false;
			$scope.players = new Array();
			$scope.nicknames = new Array();
			$scope.users = new Array();
			$scope.game = {};
			$scope.game.winner = "resistance";
			
			
			$scope.board = Board.get({id: params.boardId});
			$scope.board.$promise.then(function(board) {
				
				// Populate nickname map
				jQuery.each(board.players, function(index, value) {
					nickMap[value.nickname] = value;
				});
				
				// Sort by nickname
				$scope.board.players.sort(compareNickname);
			});
			
			
			/*
			 * Date Picker Settings
			 */
			$scope.datePopup = {
				    opened: false
			};
			$scope.game.date = new Date();
			$scope.minDate = new Date();
			$scope.openDatePicker = function() {
			    $scope.datePopup.opened = true;
			};
			
			/*
			 * Time Picker Settings
			 */
			$scope.game.time = new Date();
			
			/*
			 * Clicks
			 */
			$scope.addPlayer = function() {
				if($scope.selectedPlayer !== "") {
					var player = nickMap[$scope.selectedPlayer];
					player.isSpy = $scope.selectedPlayerSpy;
					if($scope.nicknames.includes(player.nickname)) {
						return;
					}
					$scope.players.push(player);
					$scope.nicknames.push(player.nickname);
					$scope.selectedPlayer = "";
					$scope.selectedPlayerSpy = false;
				}
			}
			
			$scope.moveUp = function(index) {
				if(index > 0) {
					var swappedPlayer = $scope.players[index - 1];
					$scope.players[index - 1] = $scope.players[index];
					$scope.players[index] = swappedPlayer;
				}
			}
			
			$scope.moveDown = function(index) {
				if(index < $scope.players.length - 1) {
					var swappedPlayer = $scope.players[index + 1];
					$scope.players[index + 1] = $scope.players[index];
					$scope.players[index] = swappedPlayer;
				}
			}
			
			$scope.deletePlayer = function(index) {
				$scope.players.splice(index, 1);
			}
			
			$scope.createGame = function() {
				$scope.game.startTime = [$scope.game.date.getFullYear(), $scope.game.date.getMonth() + 1, 
				                         $scope.game.date.getDate(), $scope.game.time.getHours(), 
				                         $scope.game.time.getMinutes(), 0, 0];
				$scope.game.boardId = $scope.board.id;
				
				// Set winner
				if($scope.game.winner === "resistance") {
					$scope.game.resistanceWin = true;
				}
				else {
					$scope.game.resistanceWin = false;
				}
				
				$scope.game.players = new Array();
				$scope.game.spies = new Array();
				
				angular.forEach($scope.players, function(value, key) {
					this.push(value.userId);
				}, $scope.game.players);
				
				angular.forEach($scope.players, function(value, key) {
					if(value.isSpy) {
						this.push(value.userId);
					}
				}, $scope.game.spies);
				
				if(!validateGame()) {
					alert('Game is not valid.');
				}
				else {
					Game.save($scope.game, function() {
						alert('Success');
						$location.path('/boarddetails');
					}, function() {
						alert('Failed');
					});
				}
				
				
				
			}
			
			/*
			 * Internal functions
			 */
			function compareNickname(a, b) {
				  if (a.nickname < b.nickname)
				    return -1;
				  else if (a.nickname > b.nickname)
				    return 1;
				  else 
				    return 0;
			}
			
			function validateGame() {
				var n = $scope.game.players.length;
				var s = $scope.game.spies.length;
				switch(n) {
					case 5: 
					case 6:	
						if(s == 2) {
							return true;
						}
						break;
					case 7: 
					case 8:
					case 9:
						if(s == 3) {
							return true;
						}
						break;
					case 10:
						if(s == 4) {
							return true;
						}
						break;
					default:
						return false;
				}
			}
			
		} ]);
