package com.tavant.trainer.service;

import java.util.List;

public class TrainResponseData {
	private int statusCode;
	private List<String> trainingList;
	public List<String> getTrainingList() {
		return trainingList;
	}
	public void setTrainingList(List<String> trainingList) {
		this.trainingList = trainingList;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	@Override
	public String toString() {
		return "TrainResponseData [statusCode=" + statusCode + ", response=" + trainingList + "]";
	}
}
