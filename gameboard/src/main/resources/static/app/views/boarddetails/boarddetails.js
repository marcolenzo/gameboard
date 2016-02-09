'use strict';

angular.module('myApp.boarddetails', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/boarddetails', {
		templateUrl : 'app/views/boarddetails/boarddetails.html',
		controller : 'BoardDetailsCtrl'
	});
} ])

.controller('BoardDetailsCtrl',	[ '$scope', '$location', 'User', 'Gameboard', function($scope, $location, User, Gameboard) {

	var params = $location.search();
	
	$scope.createGameHref = '#/creategame?boardId=' + params.id;
	
	$scope.user = undefined;
	$scope.board = Gameboard.get({id: params.boardId});
	
	
	/**
	 * Events
	 */
	$scope.$on('user-me', function(event, data) {
		$scope.user = data;
	});
	
	$scope.$on('board-details', function(event, data) {
		$scope.board = data;
	});


}]);
