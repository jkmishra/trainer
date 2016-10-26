package com.tavant.trainer.service;

import java.util.List;

public class TrainResponseData {
	private int statusCode;
	private List<String> response;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public List<String> getResponse() {
		return response;
	}
	public void setResponse(List<String> response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "TrainResponseData [statusCode=" + statusCode + ", response=" + response + "]";
	}
}
