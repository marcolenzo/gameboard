'use strict';

angular.module('myApp.creategame', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/creategame', {
		templateUrl : 'app/views/creategame/creategame.html',
		controller : 'CreategameCtrl'
	});
} ])

.controller('CreategameCtrl',
		[ '$scope', '$location', 'Game', function($scope, $location, Game) {
			
			$scope.user = undefined;
			$scope.board = undefined;
			$scope.game = {};
			
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
			 * Events
			 */
			$scope.$on('user-me', function(event, data) {
				$scope.user = data;
			});
			
			$scope.$on('current-board', function(event, data) {
				$scope.board = data;
				$scope.game.boardId = data.id;
			});
			
			/*
			 * Create Game
			 */
			$scope.createGame = function() {
				$scope.game.startTime = [$scope.game.date.getFullYear(), $scope.game.date.getMonth() + 1, 
				                         $scope.game.date.getDate(), $scope.game.time.getHours(), 
				                         $scope.game.time.getMinutes(), 0, 0];
				Game.save($scope.game, function() {
					alert('Success');
					$location.path('/boarddetails');
				}, function() {
					alert('Failed');
				})
			}
			
		} ]);
