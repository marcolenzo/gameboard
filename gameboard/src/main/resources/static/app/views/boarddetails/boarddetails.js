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
	
	$scope.user = undefined;
	$scope.board = undefined;
	// assume true to avoid flickering.
	$scope.isMember = true;
		
	$rootScope.user.$promise.then(function(user){
		$scope.user = user;
		$scope.board = Gameboard.get({id: params.boardId});
		$scope.board.$promise.then(function (board) {
			if(board.users.includes($scope.user.id)) {
				$scope.isMember = true;
			}
			else {
				$scope.isMember = false;
			}
		});
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
	
}]);
