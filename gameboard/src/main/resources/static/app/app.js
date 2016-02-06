'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngResource',                       
  'ngRoute',
  'myApp.services',
  'myApp.home',
  'myApp.dashboard',
  'myApp.createboard',
  'myApp.version',
  'ui.bootstrap',
  'ngTagsInput'
]).

config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/dashboard'});
}]).


controller('MainCtrl', ['$scope', '$location', 'User', function($scope, $location, User) {
	
}]);


