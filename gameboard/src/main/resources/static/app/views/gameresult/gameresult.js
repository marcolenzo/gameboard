'use strict';

angular.module('myApp.gameresult', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/gameresult', {
		templateUrl : 'app/views/gameresult/gameresult.html',
		controller : 'GameResultCtrl'
	});
} ])

.controller('GameResultCtrl',
		[ '$scope', '$location', 'Board', 'User', function($scope, $location, Board, User) {
			
			var params = $location.search();
			var nickMap = {}; 
			
			$scope.selectedPlayer = "";
			$scope.selectedPlayerSpy = false;
			$scope.players = new Array();
			$scope.nicknames = new Array();
			$scope.users = new Array();
			$scope.game = {};
			
			
			$scope.board = Board.get({id: params.boardId});
			$scope.board.$promise.then(function(board) {
				var allUsers = User.query();
				allUsers.$promise.then(function(users) {
					angular.forEach(users, function(value, key) {
						if(board.users.includes(value.id)) {
							this.push(value);
							nickMap[value.nickname] = value;
						}
					}, $scope.users);
					$scope.users.sort(compareNickname);
				});
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
			
			function compareNickname(a, b) {
				  if (a.nickname < b.nickname)
				    return -1;
				  else if (a.nickname > b.nickname)
				    return 1;
				  else 
				    return 0;
			}
			
		} ]);
