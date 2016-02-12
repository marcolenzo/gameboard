'use strict';

angular.module('myApp.boarddetails', [ 'ngRoute', 'ngTagsInput' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/boarddetails', {
		templateUrl : 'app/views/boarddetails/boarddetails.html',
		controller : 'BoardDetailsCtrl'
	});
} ])

.controller('BoardDetailsCtrl',	[ '$scope', '$location', '$route', '$rootScope', 'User', 'Gameboard', 'Game', 
                               	  function($scope, $location, $route, $rootScope, User, Gameboard, Game) {

	var params = $location.search();
	
	$scope.createGameHref = '#/creategame?boardId=' + params.id;
	
	$scope.user = $rootScope.user;
	$scope.board = Gameboard.get({id: params.boardId});
	$scope.isMember = false;
	
	$scope.board.$promise.then(function (board) {
		if(board.users.includes($scope.user.id)) {
			$scope.isMember = true;
		}
	});
	
	/**
	 * Events
	 */
	$scope.$on('user-me', function(event, data) {
		$scope.user = data;
	});
	
	$scope.$on('current-board', function(event, data) {
		$scope.board = data;
	});
	
	$scope.joinBoard = function() {
		$scope.board.users.push($scope.user.id);
		Gameboard.update({ id: $scope.board.id }, $scope.board, function() {
			alert('Success!');
			$route.reload();
		}, function() {
			alert('Failed!');
			$route.reload();
		});
	}
	
	/*
	 * Internal functions.
	 */
	function checkMembership() {
		if($scope.user != undefined && $scope.user.$resolved && $scope.board != undefined && $scope.board.$resolved && $scope.board.users.includes($scope.user.id)) {
			$scope.isMember = true;
		}
	}
	
	checkMembership();

}]);
