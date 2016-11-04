package com.tavant.trainer.model;

import java.util.List;

public class IntentData {
	private int statusCode;
	private List<String> dataList;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		return "IntentData [statusCode=" + statusCode + ", dataList=" + dataList + "]";
	} 

}
