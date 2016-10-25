'use strict';

App.factory('BookMasterService', ['$http', '$q', function($http, $q){

            

	return {
		    
			validateData: function(data) {
				/*var deferred = $q.defer();
				var data = {
					"data":{
						"statusCode":'200',
						"response":"success"
					}
				};
				deferred.resolve(data);
				return deferred.promise;*/

				return $http({
					//url: 'http://10.193.68.20:8080/trainer/data/validate',
					url: 'http://localhost:8080/trainer/data/validate',
			        method: "POST",
			        data: data,
			        headers: {
				        'Content-Type': 'application/json',
				        'Access-Control-Allow-Origin': '*'
				    }
				})


					
					
			},

			trainData: function(data) {
				var deferred = $q.defer();
				var data = 
						[{
						"statusCode":'1',
						"response":"P  Person etity found and ready for search1"
					   },
					   {
						"statusCode":'1',
						"response":"P  Person etity found and ready for search1"
					   }];
			
				deferred.resolve(data);
				return deferred.promise;

				/*return $http({
					//url: 'http://10.193.68.20:8080/trainer/data/validate',
					url: 'http://localhost:8080/trainer/data/validate',
			        method: "POST",
			        data: data,
			        headers: {
				        'Content-Type': 'application/json',
				        'Access-Control-Allow-Origin': '*'
				    }
				})
		*/
                 
       //           return $http.post('http://10.193.68.20:8080/trainer/data/validate', data)
							// .then(
							// 		function(response){
							// 			console.log("I am in success");
                                        
							// 			return deferred.resolve(data);
							// 			//return response.data;
							// 		}, 
							// 		function(errResponse){
							// 			console.error('Error while creating user');
							// 			console.log("I am in failure");
							// 			data = {"statusCode":'200',"response":"success"};
							// 			return deferred.reject(data);
							// 			//return $q.reject(errResponse);
							// 		}
							// );


					
					
			}
		    
		    
	}

}]);
