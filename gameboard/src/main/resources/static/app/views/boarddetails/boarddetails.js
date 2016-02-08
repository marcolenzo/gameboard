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
	if(params.id == null) {
		alert('Fuck up!');
	}
	
	$scope.createGameHref = '#/creategame?boardId=' + params.id;
	
	$scope.user = User.get({username: 'me'});
	
	$scope.board = Gameboard.get({id: params.id});
	$scope.tags = new Array();
	$scope.users = User.query({
		nicknameOnly : 'true'
	});
	$scope.isAdmin = false;
	
	$scope.board.$promise.then(function(board) {
		angular.forEach(board.users, function(value, key) {
			this.push({text: value})
		}, $scope.tags);
		
		$scope.user.$promise.then(function(data) {
			if(board.admins.includes(data.nickname)) {
				$scope.isAdmin = true;
			}
		});
	});
	
	$scope.getMatchingNicknames = function(query) {
		return jQuery.grep($scope.users, function(n, i) {
			if (n.indexOf(query) > -1) {
				return true;
			}
			return false;
		});
	}


}]);
