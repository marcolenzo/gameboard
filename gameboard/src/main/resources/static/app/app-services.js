'use strict';

angular.module('myApp.services', ['ngRoute'])

.service('User', [ '$resource', function(resource) {
	return resource('/api/user/:username/', null, {
		'update': { method: 'PUT' }
	});
	// Note resource ignores trailing slash... need to switch to $http
}])

.service('Board', [ '$resource', function(resource) {
	return resource('/api/board/:id/', null, {
		'update': { method: 'PUT' },
		'join': { method: 'POST', url: '/api/board/:id/join' }
	});
}])

.service('Action', [ '$resource', function(resource) {
	return resource('/api/action/:id/', null, {
		'update': { method: 'PUT' }
	});
}])

.service('Game', [ '$resource', function(resource) {
	return resource('/api/game/:id/', null, {
		'update': { method: 'PUT' },
		'vote': { method: 'POST', url: '/api/game/:id/vote' }
	});
}]);
