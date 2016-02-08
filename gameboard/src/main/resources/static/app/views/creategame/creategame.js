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

			var params = $location.search();
			if(params.boardId == null) {
				alert('Fuck up!');
			}
			
			$scope.createGameHref = '#/creategame?boardId=' + params.boardId;

		} ]);
