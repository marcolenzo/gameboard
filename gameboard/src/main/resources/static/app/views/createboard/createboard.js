'use strict';

angular.module('myApp.createboard', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	 $routeProvider.when('/createboard', {templateUrl: 'app/views/createboard/createboard.html', controller: 'CreateboardCtrl'});
}])

.controller('CreateboardCtrl', [function() {

}]);