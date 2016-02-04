'use strict';

angular.module('myApp.dashboard', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	 $routeProvider.when('/dashboard', {templateUrl: 'app/views/dashboard/dashboard.html', controller: 'DashboardCtrl'});
}])

.controller('DashboardCtrl', [function() {

}]);