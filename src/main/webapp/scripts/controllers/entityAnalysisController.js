'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('entityAnalysisController', ['$scope', '$timeout', '$stateParams', 'analysisService', function ($scope, $timeout, $stateParams, analysisService) {
    //$scope.consoleOutput = 'aaaa';
	  $scope.analysis = function(){
        console.log('in controller analysis controll js  : ' + $stateParams.type);
        analysisService.analysis($stateParams.type, $scope.trainingData).then(
                         function(response) {                             
                           console.log('success', response);
                           $scope.consoleOutput = response.data.type;
                          
                         },
                        function(errResponse){
                          console.error('Error while fetching Currencies');
                        }
                     );
      };
    
    
    
    
    
    
}]);
