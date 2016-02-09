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
			
			/*
			 * Date Picker Settings
			 */
			$scope.datePopup = {
				    opened: false
			};
			$scope.date = new Date();
			$scope.minDate = new Date();
			$scope.openDatePicker = function() {
			    $scope.datePopup.opened = true;
			};
			
			/*
			 * Time Picker Settings
			 */
			$scope.time = new Date();
			
			/*
			 * Events
			 */
			$scope.$on('user-me', function(event, data) {
				$scope.user = data;
			});
			
			$scope.$on('current-board', function(event, data) {
				$scope.board = data;
			});
			
			/*
			 * Create Game
			 */
			$scope.createGame = function() {
				Game.save($scope.game, function() {
					alert('Success');
				}, function() {
					alert('Failed');
				})
			}
			
		} ]);
