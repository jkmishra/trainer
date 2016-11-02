angular.module('sbAdminApp').factory('analysisService', ['$http', function($http){
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
  			
       analysis: function(entity, queryData) {
  		          console.log('inside analysis:  '+ entity + '     ' + queryData);
  		          var requestData = {entity: entity, queryData: queryData};
  		  				return $http({
  		  					url: 'api/data/analysis',
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
