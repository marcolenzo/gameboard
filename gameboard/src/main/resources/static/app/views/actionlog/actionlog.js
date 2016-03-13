'use strict';

angular.module('myApp.actionlog', [ 'ngRoute', 'ui.bootstrap' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/actionlog', {
		templateUrl : 'app/views/actionlog/actionlog.html',
		controller : 'ActionLogCtrl'
	});
} ])

.controller('ActionLogCtrl',	[ '$scope', '$uibModal', 'Action', function($scope, $uibModal, Action) {
	$scope.actions = Action.query();
	
	$scope.actions.$promise.then(function(actions) {
		jQuery.each(actions, function(index, value) {
			value.time = value.datetime[2] + '/' + value.datetime[1] + '/' + value.datetime[0] + ' ' + value.datetime[3] + (value.datetime[4] < 10 ? ':0' + value.datetime[4] : ':' + value.datetime[4]);
		});
	});
	
	$scope.showArgs = function(index) {

		var modalInstance = $uibModal.open({
			animation : true,
			templateUrl : 'actionDetails.html',
			controller : 'ActionDetailsController',
			resolve : {
				details : function() {
					return $scope.actions[index].args;
				}
			}
		 });

		 modalInstance.result.then(function() {}, function() {});
	}
	
	$scope.showResult = function(index) {

		var modalInstance = $uibModal.open({
			animation : true,
			templateUrl : 'actionDetails.html',
			controller : 'ActionDetailsController',
			resolve : {
				details : function() {
					return $scope.actions[index].result;
				}
			}
		 });

		 modalInstance.result.then(function() {}, function() {});
	}
}])
	
	.controller('ActionDetailsController', [ '$scope', '$uibModalInstance', 'details', function($scope, $uibModalInstance, details) {
		$scope.details = details;
		$scope.ok = function() {
			$uibModalInstance.close();
		};
	}]);
