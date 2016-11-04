package com.tavant.trainer.service;

import java.util.List;

import com.tavant.trainer.constants.AppConstants;
import com.tavant.trainer.model.Data;
import com.tavant.trainer.model.IntentData;
import com.tavant.trainer.model.QueryData;
import com.tavant.trainer.model.QueryResponseData;

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
		resp.setTrainingList(respMessage);
		return resp;
	}

	public static QueryResponseData queryResp(QueryData data, QueryResponseData queryResp) {		
		// if (isDataValid) {
		queryResp.setStatusCode(AppConstants.SUCCESS_200); // resp.setResponse(validationMSG);
		return queryResp;
	}
	public static ResponseData answerType(QueryData data, String respMsg) {
		ResponseData resp = new ResponseData();
		// if (isDataValid) {
		resp.setStatusCode(AppConstants.SUCCESS_200); 
		resp.setResponse(respMsg);
		return resp;
	}
	public static ResponseData analysisRsp(QueryData data, String queryResp) {		
		ResponseData respData=new ResponseData();
		respData.setStatusCode(AppConstants.SUCCESS_200); 
		respData.setResponse(queryResp);
		return respData;
	}
	public static IntentData intentFileData(List<String> respMsg) {
		IntentData resp = new IntentData();	
		resp.setStatusCode(AppConstants.SUCCESS_200); 
		resp.setDataList(respMsg);
		return resp;
	}
}
