'use strict';

angular.module('myApp.createboard', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/createboard', {
		templateUrl : 'app/views/createboard/createboard.html',
		controller : 'CreateboardCtrl'
	});
} ])

.controller('CreateboardCtrl',
		[ '$scope', '$location', 'User', 'Board', function($scope, $location, User, Board) {

			var _selected;
			var nickMap = {};
			
			

			$scope.board = {};
			$scope.board.type = "RESISTANCE";
			$scope.board.name = "";
			$scope.tags = undefined;
			
			$scope.users = User.query();
			$scope.nicknames = new Array();
			
			$scope.users.$promise.then(function(users) {
				angular.forEach(users, function(value, key) {
					this.push(value.nickname);
					nickMap[value.nickname] = value;
				}, $scope.nicknames);
			});
			

			$scope.getMatchingStates = function(query) {
				return jQuery.grep($scope.nicknames, function(n, i) {
					if (n.indexOf(query) > -1) {
						return true;
					}
					return false;
				});
			}

			$scope.createBoard = function() {
				var players = new Array();
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
				}, players);
				 

				$scope.board.players = players;

				Board.save($scope.board, function() {
					alert("Success");
					$location.path('/dashboard');
				}, function() {
					alert("Fail");
				});
			}

		} ]);
