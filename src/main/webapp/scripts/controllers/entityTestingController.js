'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('entityTestingController', ['$scope', '$timeout', '$stateParams', 'testAppService', function ($scope, $timeout, $stateParams, testAppService) {
    //$scope.consoleOutput = 'aaaa';
	  $scope.testData = function(){
        console.log('in controller testData : ' + $stateParams.type);
        testAppService.testData($stateParams.type, $scope.queryData).then(
                         function(response) {                       
                           $scope.items = response.data.dataList
                           .map(function (queryDataTest) {                             
                               return queryDataTest.queryData+" :: "+queryDataTest.entity;
                           })
                           .join('\n');             
                           
                           console.log('success', response.data.dataList);
                         },
                        function(errResponse){
                          console.error('Error while fetching Currencies');
                        }
                     );
      };
    
    
    
    
    
    
}]);
