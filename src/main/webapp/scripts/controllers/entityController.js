'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
  .controller('entityController', ['$scope', '$timeout', '$stateParams', 'appService', function ($scope, $timeout, $stateParams, appService) {
    //$scope.consoleOutput = 'aaaa';
    appService.getPageInitData($stateParams.type).then(
                     function(response) {
                        $scope.instructions = response.data;
                        console.log('success of instruction: ', response.data);
                     },
                    function(errResponse){
                      console.error('Error while fetching Currencies');
                    }
                 );
    $scope.validate = function(){
      console.log('in controller validate : ' + $stateParams.type);
      appService.validateData($stateParams.type, $scope.trainingData).then(
                       function(response) {
                          $scope.consoleOutput = response.data.response;
                          console.log('success', response.data.response);
                       },
                      function(errResponse){
                        console.error('Error while fetching Currencies');
                      }
                   );
    };

    $scope.trainData = function(){
      console.log('in controller trainData : ' + $stateParams.type);
      appService.trainData($stateParams.type, $scope.trainingData).then(
                       function(response) {
                          $scope.consoleOutput = response.data.trainingData;
                          $scope.trainedList = response.data.trainingList; 
                          console.log('success', response);
                       },
                      function(errResponse){
                        console.error('Error while fetching Currencies');
                      }
                   );
    };
    $scope.saveData = function(){
      console.log('in controller saveData : ' + $stateParams.type);
      appService.saveData($stateParams.type, $scope.trainingData).then(
                       function(response) {
                         $scope.consoleOutput = response.data.trainingData;
                         console.log('success', response);
                       },
                      function(errResponse){
                        console.error('Error while fetching Currencies');
                      }
                   );
    };
}]);
