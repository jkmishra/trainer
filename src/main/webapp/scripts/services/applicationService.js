angular.module('sbAdminApp').factory('appService', ['$http', function($http){
  	return {
      getPageInitData: function(entity) {
        console.log('inside getPageInitData:  '+ entity);
        var requestURI = 'api/data/instructions/' + entity;
        return $http({
          url: requestURI,
              method: "GET",
              headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        })
      },
  validateData: function(entity, trainingData) {
          console.log('inside validateData:  '+ entity + '     ' + trainingData);
          var requestData = {entity: entity, trainingData: trainingData};
  				return $http({
  					url: 'api/data/validate',
  			        method: "POST",
  			        data: requestData,
  			        headers: {
  				        'Content-Type': 'application/json',
  				        'Access-Control-Allow-Origin': '*'
  				    }
  				})
  			},
        trainData: function(entity, trainingData) {
          console.log('inside trainData:  '+ entity + '     ' + trainingData);
          var requestData = {entity: entity, trainingData: trainingData};
  				return $http({
  					url: 'api/data/train',
  			        method: "POST",
  			        data: requestData,
  			        headers: {
  				        'Content-Type': 'application/json',
  				        'Access-Control-Allow-Origin': '*'
  				    }
  				})
  			},
        saveData: function(entity, trainingData) {
          console.log('inside saveData:  '+ entity + '     ' + trainingData);
          var requestData = {entity: entity, trainingData: trainingData};
  				return $http({
  					url: 'api/data/save',
  			        method: "POST",
  			        data: requestData,
  			        headers: {
  				        'Content-Type': 'application/json',
  				        'Access-Control-Allow-Origin': '*'
  				    }
  				})
  			}
  			
  	}
  }]);
