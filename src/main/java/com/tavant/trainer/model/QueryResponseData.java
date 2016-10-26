package com.tavant.trainer.model;

public class QueryResponseData {
	private int statusCode;
	private String dataStr;
	private String type;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getDataStr() {
		return dataStr;
	}
	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "QueryResponseData [statusCode=" + statusCode + ", dataStr=" + dataStr + ", type=" + type + "]";
	}
	
}
