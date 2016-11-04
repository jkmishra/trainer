'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description # MainCtrl Controller of the sbAdminApp
 */
angular
	.module('sbAdminApp')
	.controller(
		'intentController',
		[
			'$scope',
			'$timeout',
			'$stateParams',
			'intentAppService',
			function($scope, $timeout, $stateParams,
				intentAppService) {			
			    intentAppService
				    .intent()
				    .then(
					    function(response) {						
						$scope.dataList = response.data.dataList;
						console
							.log('success',
								response);
					    },
					    function(errResponse) {
						console
							.error('Error while fetching Currencies');
					    });

			} ]);
