angular.module('sbAdminApp').factory('testAppService', ['$http', function($http){
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
  			
       testData: function(entity, queryData) {
  		          console.log('inside testData:  '+ entity + '     ' + queryData);
  		          var requestData = {entity: entity, queryData: queryData};
  		  				return $http({
  		  					url: 'api/data/testData',
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
