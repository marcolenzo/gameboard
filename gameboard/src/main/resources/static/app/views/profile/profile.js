'use strict';

angular.module('myApp.profile', [ 'ngRoute', 'ui.bootstrap' ])
	.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/profile', {
			templateUrl : 'app/views/profile/profile.html',
			controller : 'ProfileCtrl'
		});
	}])
	
	.controller('ProfileCtrl', ['$scope', '$rootScope','User', 'Board', '$uibModal', function($scope, $rootScope, User, Board, $uibModal) {
		
		$scope.boardStats = new Array();
		$scope.user =  User.get({username: 'me'});
		
		$scope.user.$promise.then(function() {
			init();
		});
			
		function init() {
			$scope.boardStats = new Array();
			$scope.boards = Board.query({user : $scope.user.id});
			$scope.boards.$promise.then(function() {
				jQuery.each($scope.boards, function(index, value) {
					var boardName = value.name;
					jQuery.each(value.players, function(index, value) {
						if (value.userId === $scope.user.id) {
							value.boardName = boardName;
							$scope.boardStats.push(value);
						}
						value.wp = value.matchesPlayed == 0 ? 0	: value.matchesWon / value.matchesPlayed * 100;
						value.wrp = value.matchesPlayedAsResistance == 0 ? 0 : value.matchesWonAsResistance	/ value.matchesPlayedAsResistance * 100;
						value.wsp = value.matchesPlayedAsSpy == 0 ? 0 : value.matchesWonAsSpy / value.matchesPlayedAsSpy * 100;

						value.wp = (Math.round(value.wp * 100, -2) / 100).toFixed(2);
						value.wrp = (Math.round(value.wrp * 100,-2) / 100).toFixed(2);
						value.wsp = (Math.round(value.wsp * 100, -2) / 100).toFixed(2);
						
						if (value.positionVariation > 0) {
							value.positionChangeMarker = '˄';
						} 
						else if (value.positionVariation < 0) {
							value.positionChangeMarker = '˅';
						}
					});
				});
			});
		}
		
		$scope.editNickname = function() {

		var modalInstance = $uibModal.open({
			animation : true,
			templateUrl : 'changeNickname.html',
			controller : 'ChangeNicknameController',
			resolve : {
				user : function() {
					return $scope.user;
				}
			}
		 });

		 modalInstance.result.then(function(user) {
			 $scope.user = user;
			 init();
		 }, function() {
			console.log('Modal dismissed');
		});
	};
}]);

angular.module('myApp.profile').controller('ChangeNicknameController', [ '$scope', '$rootScope', '$uibModalInstance', 'User', 'user', function($scope, $rootScope, $uibModalInstance, User, user) {
	$scope.nickname = '';

	$scope.ok = function() {
		user.nickname = $scope.nickname;
		User.update({ username : user.id }, user, function(user) {
			$rootScope.$broadcast('user-updated', user);
			$uibModalInstance.close(user);
		}, function() {
			alert("Fail");
		});
	};

	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
}]);
