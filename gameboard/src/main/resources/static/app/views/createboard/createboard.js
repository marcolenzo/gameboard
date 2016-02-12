'use strict';

angular.module('myApp.createboard', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/createboard', {
		templateUrl : 'app/views/createboard/createboard.html',
		controller : 'CreateboardCtrl'
	});
} ])

.controller('CreateboardCtrl',
		[ '$scope', '$location', 'User', 'Gameboard', function($scope, $location, User, Gameboard) {

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
					nickMap[value.nickname] = value.id;
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
				var users = new Array();
				angular.forEach($scope.tags, function(value, key) {
					this.push(nickMap[value.text]);
				}, users);
				 

				$scope.board.users = users;

				Gameboard.save($scope.board, function() {
					alert("Success");
					$location.path('/dashboard');
				}, function() {
					alert("Fail");
				});
			}

		} ]);
