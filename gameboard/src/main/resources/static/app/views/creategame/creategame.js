'use strict';

angular.module('myApp.creategame', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/creategame', {
		templateUrl : 'app/views/creategame/creategame.html',
		controller : 'CreategameCtrl'
	});
} ])

.controller('CreategameCtrl',
		[ '$scope', '$location', 'User', 'Gameboard', function($scope, $location, User, Gameboard) {
			
			$scope.popup1 = {
				    opened: false
			};
			
			$scope.isRecurring = false;

			var params = $location.search();
			if(params.boardId == null) {
				alert('Fuck up!');
			}
			
			$scope.createGameHref = '#/creategame?boardId=' + params.boardId;
			$scope.board = Gameboard.get({id: params.boardId});
			
			$scope.open1 = function() {
			    $scope.popup1.opened = true;
			};

		} ]);
