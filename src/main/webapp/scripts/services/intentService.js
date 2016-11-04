angular.module('sbAdminApp').factory('intentAppService',
	[ '$http', function($http) {
	    return {		
		intent : function() {
		    console.log('inside intent: ');
		    // var requestData = {entity: entity, queryData:
		    // queryData};
		    return $http({
			url : 'api/data/intentTest',
			method : "GET",
			// data: requestData,
			headers : {
			    'Access-Control-Allow-Origin' : '*'
			}
		    })
		}

	    }
	} ]);
