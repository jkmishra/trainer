package com.tavant.trainer;

import java.util.List;

public class ResponseBuilder {

	public static ResponseData validatorResponse(Data data, String validationMSG) {
		ResponseData resp = new ResponseData();
		// if (isDataValid) {
		resp.setStatusCode(AppConstants.SUCCESS_200);
		resp.setResponse(validationMSG);
		return resp;
	}

	public static TrainResponseData trainingResponse(Data data, List<String> respMessage, boolean isSuccess) {
		TrainResponseData resp = new TrainResponseData();
		if (isSuccess) {
			resp.setStatusCode(AppConstants.SUCCESS_200);
		} else {
			resp.setStatusCode(AppConstants.FAIL_200);
		}
		resp.setResponse(respMessage);
		return resp;
	}

}
