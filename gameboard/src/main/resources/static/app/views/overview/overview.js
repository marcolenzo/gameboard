'use strict';

angular.module('myApp.overview', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	 $routeProvider.when('/overview', {templateUrl: 'app/views/overview/overview.html', controller: 'OverviewCtrl'});
}])

.controller('OverviewCtrl', ['$scope', 'Board', 'User', function($scope, Board, User) {
    $scope.user = User.get({username: 'me'});
    $scope.gameboards = undefined;
	
    $scope.user.$promise.then(function(data) {
		$scope.gameboards = Board.query({user : data.id});
		
		$scope.gameboards.$promise.then(function(boards) {
			angular.forEach(boards, function(value, key) {
				if(value.admins.includes(data.id)) {
					value.role = 'ADMINISTRATOR';
				}
				else {
					value.role = 'USER';
				}
			});
		});
	});
	
}]);