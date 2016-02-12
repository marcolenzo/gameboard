'use strict';

angular.module('myApp.services', ['ngRoute'])

.service('User', [ '$resource', function(resource) {
	return resource('/api/user/:username/');
	// Note resource ignores trailing slash... need to switch to $http
}])

.service('Board', [ '$resource', function(resource) {
	return resource('/api/board/:id/', null, {
		'update': { method: 'PUT' }
	});
	// Note resource ignores trailing slash... need to switch to $http
}])

.service('Game', [ '$resource', function(resource) {
	return resource('/api/game/:id/');
	// Note resource ignores trailing slash... need to switch to $http
}]);
