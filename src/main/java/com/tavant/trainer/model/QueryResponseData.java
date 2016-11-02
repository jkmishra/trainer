package com.tavant.trainer.model;

import java.util.List;

public class QueryResponseData {
	private int statusCode;
	private List<QueryData> dataList;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public List<QueryData> getDataList() {
		return dataList;
	}
	public void setDataList(List<QueryData> dataList) {
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		return "QueryResponseData [statusCode=" + statusCode + ", dataList=" + dataList + "]";
	}
		
}
