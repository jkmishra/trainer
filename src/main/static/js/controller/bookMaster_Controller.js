'use strict';

App.controller('BookMasterController', ['$scope', 'BookMasterService', function($scope, BookMasterService) {
          var self = this;
          self.entities;
          self.data;
          self.changeEntity ='person';
          self.textData;
              
          self.validateData = function(){

             console.log(self.textData);
             self.textData = self.textData != '' || self.textData!= undefined ? self.textData:'';
             self.data={entity: self.changeEntity ,trainingData:self.textData};
             console.log(self.data);
              BookMasterService.validateData(self.data)
                  .then(
                       function(d) {
                          self.entities = d.data.response;
                       },
                      function(errResponse){
                        console.error('Error while fetching Currencies');
                      }
                   );
          };


          self.trainData = function(){

             console.log(self.textData);
             self.textData = self.textData != '' || self.textData!= undefined ? self.textData:'';
             self.data={entity: self.changeEntity ,trainingData:self.textData};
             console.log(self.data);
              BookMasterService.trainData(self.data)
                  .then(
                       function(d) {
                          self.results = d;
                       },
                      function(errResponse){
                        console.error('Error while fetching Currencies');
                      }
                   );
          };
          
          self.changePerson = function (d){
              self.reset();
              $scope.reloadRoute = function() {
                   $window.location.reload();
                }
               
              self.changeEntity = d;
          };

          self.changeMoney = function (d){
              self.reset();
              $scope.reloadRoute = function() {
                   $window.location.reload();
                }
              self.changeEntity = d;
          };

          self.changeAnswer = function (d){
            self.reset();
              $scope.reloadRoute = function() {
                   $window.location.reload();
                }

              self.changeEntity = d;
          };

          
          self.reset = function(){
              self.entities='';
              self.textData='';
          };

      }]);
