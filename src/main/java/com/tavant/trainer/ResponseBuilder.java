package com.tavant.trainer;

public class ResponseBuilder {

	public static ResponseData validatorResponse(Data data, String validationMSG) {
		ResponseData resp = new ResponseData();
		//if (isDataValid) {
			resp.setStatusCode(AppConstants.SUCCESS_200);
			resp.setResponse(validationMSG);
		/*} else {
			resp.setStatusCode(AppConstants.FAIL_200);
			resp.setResponse(AppConstants.INVALID_DATA);
		}*/
		return resp;
	}

}
