'use strict';

angular.module('myApp.dashboard', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	 $routeProvider.when('/dashboard', {templateUrl: 'app/views/dashboard/dashboard.html', controller: 'DashboardCtrl'});
}])

.controller('DashboardCtrl', ['$scope', 'Gameboard', 'User', function($scope, Gameboard, User) {
    $scope.user = User.get({username: 'me'});
    $scope.gameboards = undefined;
	
    $scope.user.$promise.then(function(data) {
		$scope.gameboards = Gameboard.query({user : data.nickname});
		
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