'use strict';

angular.module('myApp.services', ['ngRoute'])

.service('User', [ '$resource', function(resource) {
	return resource('/api/user/:username/');
	// Note resource ignores trailing slash... need to switch to $http
}])

.service('Gameboard', [ '$resource', function(resource) {
	return resource('/api/gameboard/:id/');
	// Note resource ignores trailing slash... need to switch to $http
}]);
